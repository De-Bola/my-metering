package com.enefit.metering.service;

import com.enefit.metering.exceptions.CustomerNotFoundException;
import com.enefit.metering.models.Customer;
import com.enefit.metering.models.MeteringPoint;
import com.enefit.metering.repository.CustomerRepository;
import com.enefit.metering.repository.MeteringPointsRepository;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeteringPointService {

    private static final Logger logger = LoggerFactory.getLogger(MeteringPointService.class);

    private final MeteringPointsRepository meteringPointsRepository;
    private final CustomerRepository customerRepository;
    private final SecurityService securityService;

    public MeteringPointService(MeteringPointsRepository meteringPointsRepository,
                                CustomerRepository customerRepository,
                                SecurityService securityService) {
        this.meteringPointsRepository = meteringPointsRepository;
        this.customerRepository = customerRepository;
        this.securityService = securityService;
    }

    public MeteringPoint addMeteringPoint(String address) {
        String username = securityService.getCurrentUsername();
        logger.info("Creating metering point at address: {} for user: {}", address, MaskingUtil.mask(username));

        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with username " +
                        MaskingUtil.mask(username) + " not found"));

        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setAddress(address);
        meteringPoint.setCustomer(customer);
        MeteringPoint savedMeteringPoint = meteringPointsRepository.save(meteringPoint);
        logger.info("Metering point created with id: {} for user: {}",
                savedMeteringPoint.getMeteringPointId(), MaskingUtil.mask(username));
        return savedMeteringPoint;
    }

    public List<MeteringPoint> findCustomerMeteringPoints() {
        String username = securityService.getCurrentUsername();
        logger.info("Retrieving metering points for customer with username: {}", MaskingUtil.mask(username));
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with username " +
                        MaskingUtil.mask(username) + " not found"));
        return meteringPointsRepository.findMeteringPointsByCustomerCustomerId(customer.getCustomerId());
    }
}
