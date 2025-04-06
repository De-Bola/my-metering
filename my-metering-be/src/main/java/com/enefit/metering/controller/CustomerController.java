package com.enefit.metering.controller;

import com.enefit.metering.controller.response.SuccessResponse;
import com.enefit.metering.models.CustomerDto;
import com.enefit.metering.service.CustomerService;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the customer's unique identifier.
     * @return a response containing the customer details.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<SuccessResponse<CustomerDto>> getCustomer(@PathVariable Long customerId) {
        logger.info("Retrieving customer with ID: {}", customerId);
        CustomerDto customer = customerService.findCustomerById(customerId);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.toString(), customer, "Customer found!"));
    }

    /**
     * Retrieves details of the currently authenticated customer.
     *
     * @param userDetails the authenticated user's details.
     * @return a response containing the customer details.
     */
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<CustomerDto>> getLoggedInCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Retrieving logged-in customer: {}", MaskingUtil.mask(userDetails.getUsername()));
        CustomerDto customer = customerService.findUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK.toString(), customer, "Customer found!"));
    }
}
