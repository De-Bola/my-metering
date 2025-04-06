package com.enefit.metering.service;

import com.enefit.metering.models.MarketPriceData;
import com.enefit.metering.models.MarketPriceDataPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MarketPriceService {

    private static final Logger logger = LoggerFactory.getLogger(MarketPriceService.class);
    public static final String MIDNIGHT_VALUE = "-01T00:00:00";

    private final WebClient webClient;
    private final String path;
    private final String timeZoneOffset;
    private final String baseUrl;

    public MarketPriceService(
            @Value("${estfeed.elering.market-prices.baseurl}") String baseUrl,
            @Value("${estfeed.elering.market-prices.path}") String path,
            @Value("${estfeed.elering.market-prices.offset}") String timeZoneOffset,
            WebClient.Builder webClientBuilder) {
        this.path = path;
        this.timeZoneOffset = timeZoneOffset;
        this.baseUrl = baseUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public List<MarketPriceDataPoint> getRawMarketPrices(String startDateTime, String endDateTime) {
        logger.info("Fetching raw market prices from {} to {}", startDateTime, endDateTime);

        List<MarketPriceDataPoint> marketPriceDataPoints = new ArrayList<>();
        marketPriceDataPoints = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("startDateTime", startDateTime)
                        .queryParam("endDateTime", endDateTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MarketPriceDataPoint.class)
                .collectList()
                .block();

        logger.info("Here are the marketData retrieved by proxy {}", marketPriceDataPoints);

        return marketPriceDataPoints;
    }

    @Cacheable("marketPrices")
    public MarketPriceData getAggregatedMarketPrices(String startDateTime, String endDateTime) {

        List<MarketPriceDataPoint> rawData = getRawMarketPrices(startDateTime, endDateTime);
        logger.info("Aggregating {} raw data points by month.", rawData.size());

        // Use a TreeMap to group by year-month (e.g., "2024-06") in sorted order
        Map<String, List<MarketPriceDataPoint>> groups = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (MarketPriceDataPoint dp : rawData) {
            String yearMonth = dp.getFromDateTime().format(formatter);
            groups.computeIfAbsent(yearMonth, k -> new ArrayList<>()).add(dp);
        }

        List<MarketPriceDataPoint> aggregatedList = new ArrayList<>();
        for (Map.Entry<String, List<MarketPriceDataPoint>> entry : groups.entrySet()) {
            List<MarketPriceDataPoint> group = entry.getValue();
            double avgCentsPerKwh = group.stream().mapToDouble(MarketPriceDataPoint::getCentsPerKwh).average().orElse(0.0);
            double avgCentsPerKwhWithVat = group.stream().mapToDouble(MarketPriceDataPoint::getCentsPerKwhWithVat).average().orElse(0.0);
            double avgEurPerMwh = group.stream().mapToDouble(MarketPriceDataPoint::getEurPerMwh).average().orElse(0.0);
            double avgEurPerMwhWithVat = group.stream().mapToDouble(MarketPriceDataPoint::getEurPerMwhWithVat).average().orElse(0.0);

            // Use the first day of the month as the aggregated date
            OffsetDateTime aggregatedFrom = OffsetDateTime.parse(entry.getKey() + MIDNIGHT_VALUE + timeZoneOffset);
            // Set aggregated toDateTime to be the end of the month (approximated here)
            OffsetDateTime aggregatedTo = aggregatedFrom.plusMonths(1).minusNanos(1);

            MarketPriceDataPoint aggregatedPoint = new MarketPriceDataPoint(avgCentsPerKwh, avgCentsPerKwhWithVat,
                    avgEurPerMwh, avgEurPerMwhWithVat, aggregatedFrom, aggregatedTo
                    );

            aggregatedList.add(aggregatedPoint);
        }
        MarketPriceData aggregatedData = new MarketPriceData(aggregatedList);
        logger.info("Here are the aggregated marketData {}", aggregatedList);
        return aggregatedData;
    }
}
