package com.enefit.metering.service;

import com.enefit.metering.exceptions.CustomerNotFoundException;
import com.enefit.metering.exceptions.MeteringPointNotFoundException;
import com.enefit.metering.models.Consumption;
import com.enefit.metering.models.Customer;
import com.enefit.metering.models.MeteringPoint;
import com.enefit.metering.repository.ConsumptionRepository;
import com.enefit.metering.repository.CustomerRepository;
import com.enefit.metering.repository.MeteringPointsRepository;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ConsumptionService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumptionService.class);

    private final MeteringPointsRepository meteringPtsRepo;
    private final ConsumptionRepository consumptionRepo;
    private final CustomerRepository customerRepo;
    private final SecurityService securityService;

    public ConsumptionService(MeteringPointsRepository meteringPtsRepo,
                              ConsumptionRepository consumptionRepo,
                              CustomerRepository customerRepo,
                              SecurityService securityService) {
        this.meteringPtsRepo = meteringPtsRepo;
        this.consumptionRepo = consumptionRepo;
        this.customerRepo = customerRepo;
        this.securityService = securityService;
    }

    /**
     * Adds a list of consumption records for the specified metering point.
     *
     * @param consumptions    the list of consumption records to add.
     * @param meteringPointId the identifier of the metering point.
     * @return the number of records saved.
     * @throws MeteringPointNotFoundException if the metering point is not found.
     */
    public int addConsumption(List<Consumption> consumptions, Long meteringPointId) {
        Assert.notNull(consumptions, "Consumption request must not be null");
        Assert.notEmpty(consumptions, "Consumption request must not be empty");
        logger.info("Adding {} consumption record(s) for metering point id: {}", consumptions.size(), meteringPointId);

        MeteringPoint meteringPoint = meteringPtsRepo.findById(meteringPointId)
                .orElseThrow(() -> new MeteringPointNotFoundException("Metering point with id " +
                        meteringPointId + " not found"));

        consumptions.forEach(consumption -> consumption.setMeteringPoint(meteringPoint));

        List<Consumption> savedConsumptions = consumptionRepo.saveAll(consumptions);
        logger.info("Successfully added {} consumption record(s) for metering point id: {}",
                savedConsumptions.size(), meteringPointId);
        return savedConsumptions.size();
    }

    /**
     * Retrieves consumption records for a metering point, ensuring the current user is authorized.
     *
     * @param meteringPointId the identifier of the metering point.
     * @return the list of consumption records.
     * @throws CustomerNotFoundException if the current customer is not found.
     * @throws MeteringPointNotFoundException if the metering point is not found.
     * @throws AccessDeniedException if the metering point does not belong to the current customer.
     */
    public List<Consumption> getAuthorizedConsumptionsForMeteringPoint(Long meteringPointId) {
        String username = securityService.getCurrentUsername();
        logger.info("Retrieving consumption records for metering point id: {} for user: {}",
                meteringPointId, MaskingUtil.mask(username));

        Customer customer = customerRepo.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with username " +
                        MaskingUtil.mask(username) + " not found"));

        MeteringPoint meteringPoint = meteringPtsRepo.findById(meteringPointId)
                .orElseThrow(() -> new MeteringPointNotFoundException("Metering point with id " +
                        meteringPointId + " not found"));

        if (!customer.getCustomerId().equals(meteringPoint.getCustomer().getCustomerId())) {
            logger.warn("Access denied for user {} on metering point id: {}",
                    MaskingUtil.mask(username), meteringPointId);
            throw new AccessDeniedException("You do not have permission to access this data");
        }
        logger.info("User {} authorized for metering point id: {}", MaskingUtil.mask(username), meteringPointId);
        List<Consumption> consumptions = consumptionRepo.findByMeteringPointMeteringPointId(meteringPointId);
        logger.info("Retrieved {} consumption record(s) for metering point id: {}", consumptions.size(), meteringPointId);
        return consumptions;
    }
}
