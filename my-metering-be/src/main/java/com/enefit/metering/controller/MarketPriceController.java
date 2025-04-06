package com.enefit.metering.controller;

import com.enefit.metering.models.MarketPriceData;
import com.enefit.metering.models.MarketPriceDataPoint;
import com.enefit.metering.service.MarketPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/market-prices")
public class MarketPriceController {

    private static final Logger logger = LoggerFactory.getLogger(MarketPriceController.class);
    private final MarketPriceService marketPriceService;

    public MarketPriceController(MarketPriceService marketPriceService) {
        this.marketPriceService = marketPriceService;
    }

    /**
     * Retrieves aggregated market price data for the specified time range.
     *
     * @param startDateTime the start date/time in ISO-8601 format.
     * @param endDateTime   the end date/time in ISO-8601 format.
     * @return a ResponseEntity containing a Mono list of market price data points.
     */
    @GetMapping
    public ResponseEntity<?> getMarketPrices(
            @RequestParam String startDateTime,
            @RequestParam String endDateTime) {
        logger.info("Retrieving market prices from {} to {}", startDateTime, endDateTime);
//        String dataPoints = marketPriceService.getRawMarketPricesStr(startDateTime, endDateTime);
        MarketPriceData dataPoints = marketPriceService.getAggregatedMarketPrices(startDateTime, endDateTime);
        return ResponseEntity.ok(dataPoints);
    }
}
