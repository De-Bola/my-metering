package com.enefit.metering.service;

import com.enefit.metering.models.*;
import com.enefit.metering.repository.ConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CostService {

    private static final Logger logger = LoggerFactory.getLogger(CostService.class);

    private final MarketPriceService marketPriceService;
    private final ConsumptionRepository consumptionRepository;
    private final SecurityService securityService;

    public CostService(MarketPriceService marketPriceService,
                       ConsumptionRepository consumptionRepository,
                       SecurityService securityService) {
        this.marketPriceService = marketPriceService;
        this.consumptionRepository = consumptionRepository;
        this.securityService = securityService;
    }

    /**
     * Calculates monthly cost breakdown for a given metering point over a specified time range.
     *
     * @param startDateTime   the start date/time in ISO-8601 format.
     * @param endDateTime     the end date/time in ISO-8601 format.
     * @param meteringPointId the metering point identifier.
     * @return a Mono wrapping the cost calculation result containing monthly cost details.
     */
    public CostCalculationResult calculateCost(String startDateTime, String endDateTime, Long meteringPointId) {
        MarketPriceData aggregatedPrices =
                marketPriceService.getAggregatedMarketPrices(startDateTime, endDateTime);
        // Retrieve the monthly consumption sums for the given metering point.
        List<MonthlyConsumption> monthlyConsumptions = consumptionRepository.findMonthlyConsumptionSum(meteringPointId);

        logger.info("Calculating cost for metering point id: {}", meteringPointId);

        List<MonthlyCostResult> monthlyCosts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // For each aggregated market price data point, match it with monthly consumption.
        for (MarketPriceDataPoint marketPrice : aggregatedPrices.getData()) {
            String month = marketPrice.getFromDateTime().format(formatter);

            Optional<MonthlyConsumption> matchingConsumption = monthlyConsumptions.stream()
                    .filter(mc -> {
                        // Convert the Timestamp returned by the projection into an OffsetDateTime
                        OffsetDateTime consumptionMonth = OffsetDateTime.ofInstant(
                                ((Timestamp) mc.getMonth()).toInstant(), ZoneOffset.UTC);
                        return consumptionMonth.format(formatter).equals(month);
                    })
                    .findFirst();

            if (matchingConsumption.isPresent()) {
                BigDecimal consumptionAmount = matchingConsumption.get().getTotalConsumption();
                double price = marketPrice.getEurPerMwh();
                double monthlyCost = consumptionAmount.doubleValue() * price;
                monthlyCosts.add(new MonthlyCostResult(month, consumptionAmount, price, monthlyCost));
            }
        }

        return new CostCalculationResult(monthlyCosts);
    }
}
