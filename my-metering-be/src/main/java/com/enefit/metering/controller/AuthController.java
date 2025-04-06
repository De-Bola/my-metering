package com.enefit.metering.controller;

import com.enefit.metering.controller.response.SuccessResponse;
import com.enefit.metering.exceptions.CustomerAlreadyExistsException;
import com.enefit.metering.models.CustomerDto;
import com.enefit.metering.models.JwtRequest;
import com.enefit.metering.models.JwtResponse;
import com.enefit.metering.service.CustomerService;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Registers a new customer.
     *
     * @param request the customer details.
     * @return a response containing the registered customer details.
     * @throws CustomerAlreadyExistsException if a customer with the given username already exists.
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<CustomerDto>> createUser(@RequestBody CustomerDto request)
            throws CustomerAlreadyExistsException {
        logger.info("Registration request for new user: {}", MaskingUtil.mask(request.getUsername()));
        CustomerDto savedCustomer = customerService.createCustomer(request);
        SuccessResponse<CustomerDto> response = new SuccessResponse<>(HttpStatus.OK.toString(),
                savedCustomer, "Registered successfully!");
        return ResponseEntity.ok(response);
    }

    /**
     * Authenticates a customer and creates a JWT.
     *
     * @param request the JWT request containing the username and password.
     * @return a response containing the JWT and refresh token.
     */
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtResponse<CustomerDto>>> createToken(@RequestBody JwtRequest request) {
        logger.info("Login request for user: {}", MaskingUtil.mask(request.getUsername()));
        JwtResponse<CustomerDto> jwtResponse = customerService.login(request.getUsername(), request.getPassword());
        SuccessResponse<JwtResponse<CustomerDto>> response = new SuccessResponse<>(HttpStatus.OK.toString(),
                jwtResponse, "Login successful!");
        return ResponseEntity.ok(response);
    }
}
