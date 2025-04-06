package com.enefit.metering.controller;

import com.enefit.metering.controller.response.ErrorResponse;
import com.enefit.metering.controller.response.SuccessResponse;
import com.enefit.metering.models.Consumption;
import com.enefit.metering.models.CostCalculationResult;
import com.enefit.metering.models.MeteringPoint;
import com.enefit.metering.service.ConsumptionService;
import com.enefit.metering.service.CostService;
import com.enefit.metering.service.MeteringPointService;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/metering-points")
public class MeteringPointController {

    private static final Logger logger = LoggerFactory.getLogger(MeteringPointController.class);
    private final CostService costService;
    private final MeteringPointService meteringPointService;
    private final ConsumptionService consumptionService;

    public MeteringPointController(CostService costService, MeteringPointService meteringPointService, ConsumptionService consumptionService) {
        this.costService = costService;
        this.meteringPointService = meteringPointService;
        this.consumptionService = consumptionService;
    }

    /**
     * Creates a new metering point.
     *
     * @param address the address of the metering point.
     * @return a response containing the ID of the created metering point.
     */
    @PostMapping
    public ResponseEntity<?> addMeteringPoint(@RequestBody String address) {
        if (address == null || address.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                            "Address must be provided", Instant.now()));
        }
        MeteringPoint savedMP = meteringPointService.addMeteringPoint(address);
        logger.info("Created metering point with id: {}", savedMP.getMeteringPointId());
        SuccessResponse<String> response = new SuccessResponse<>(HttpStatus.OK.toString(),
                String.valueOf(savedMP.getMeteringPointId()), "Metering point created successfully!");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves metering points for the currently authenticated customer.
     *
     * @param userDetails the authenticated user's details.
     * @return a response containing the list of metering points.
     */
    @GetMapping
    public ResponseEntity<?> getAuthorizedMeteringPoints(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Retrieving metering points for user: {}", MaskingUtil.mask(userDetails.getUsername()));
        List<MeteringPoint> meteringPoints = meteringPointService.findCustomerMeteringPoints();
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.toString(), meteringPoints,
                "Found " + meteringPoints.size() + " metering point(s)"));
    }

    /**
     * Retrieves cost calculation for a specific metering point over a given time range.
     *
     * @param startDateTime  the start date/time in ISO-8601 format.
     * @param endDateTime    the end date/time in ISO-8601 format.
     * @param meteringPointId the metering point identifier as a path variable.
     * @return a response containing a Mono of the cost calculation result.
     */
    @GetMapping("/{meteringPointId}/costs")
    public ResponseEntity<CostCalculationResult> getCosts(
            @RequestParam String startDateTime,
            @RequestParam String endDateTime,
            @PathVariable("meteringPointId") Long meteringPointId) {
        logger.info("Calculating cost for metering point id {} from {} to {}",
                meteringPointId, startDateTime, endDateTime);
        CostCalculationResult result = costService.calculateCost(startDateTime, endDateTime, meteringPointId);
        return ResponseEntity.ok(result);
    }

    /**
     * Adds multiple consumption records to the specified metering point.
     *
     * @param consumptions    the list of consumption objects to add.
     * @param meteringPointId the metering point identifier as a path variable.
     * @return a ResponseEntity with a success response.
     */
    @PostMapping("/{meteringPointId}/consumptions")
    public ResponseEntity<?> addConsumptions(@RequestBody List<Consumption> consumptions,
                                             @PathVariable("meteringPointId") Long meteringPointId) {
        if (consumptions == null || consumptions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                            "Consumption list must not be empty", Instant.now()));
        }
        int savedCount = consumptionService.addConsumption(consumptions, meteringPointId);
        logger.info("Saved {} consumption record(s) for metering point id: {}", savedCount, meteringPointId);
        SuccessResponse<String> response = new SuccessResponse<>(HttpStatus.OK.toString(),
                String.valueOf(savedCount), "Consumptions saved successfully!");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves consumption records for the specified metering point.
     *
     * @param meteringPointId the metering point identifier as a path variable.
     * @return a ResponseEntity with a list of consumption records.
     */
    @GetMapping("/{meteringPointId}/consumptions")
    public ResponseEntity<?> getConsumptions(@PathVariable("meteringPointId") Long meteringPointId) {
        logger.info("Retrieving consumptions for metering point id: {}", meteringPointId);
        List<Consumption> consumptions = consumptionService.getAuthorizedConsumptionsForMeteringPoint(meteringPointId);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.toString(), consumptions,
                "Found " + consumptions.size() + " consumption record(s) for metering point id: " + meteringPointId));
    }
}
