package com.enefit.metering.service;

import com.enefit.metering.exceptions.CustomerAlreadyExistsException;
import com.enefit.metering.exceptions.CustomerNotFoundException;
import com.enefit.metering.models.Customer;
import com.enefit.metering.models.CustomerDto;
import com.enefit.metering.models.JwtResponse;
import com.enefit.metering.repository.CustomerRepository;
import com.enefit.metering.utils.JwtUtil;
import com.enefit.metering.utils.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service class for managing customer-related operations.
 */
@Service
public class CustomerService {

    private final CustomerRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * Constructs a new CustomerService with the provided dependencies.
     *
     * @param repo                   the customer repository.
     * @param passwordEncoder        the password encoder.
     * @param jwtUtil                the JWT utility.
     * @param authenticationManager  the authentication manager.
     * @param customerDetailsService the service for loading customer details.
     */
    public CustomerService(CustomerRepository repo,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager,
                           CustomerDetailsService customerDetailsService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Creates a new customer.
     * This method saves a customer with the details provided in the {@code customerDto}.
     * It encodes the password and stores first name, last name, and username.
     *
     * @param customerDto the customer DTO containing new customer details.
     * @return a detailed CustomerDto representing the saved customer.
     * @throws CustomerAlreadyExistsException if a customer with the provided username already exists.
     */
    public CustomerDto createCustomer(CustomerDto customerDto) throws CustomerAlreadyExistsException {
        if (repo.existsByUsername(customerDto.getUsername())) {
            throw new CustomerAlreadyExistsException("User with this username already exists!");
        }
        logger.info("Creating customer with username: {}", MaskingUtil.mask(customerDto.getUsername()));

        Customer customer = new Customer();
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());

        Customer savedCustomer = repo.save(customer);

        return CustomerDto.builder()
                .customerId(savedCustomer.getCustomerId().toString())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .username(savedCustomer.getUsername())
                .build();
    }

    /**
     * Retrieves a customer by their unique identifier.
     * Access is restricted so that a logged-in user can only access their own data.
     *
     * @param customerId the customer's ID.
     * @return a detailed CustomerDto representing the found customer.
     * @throws CustomerNotFoundException if no customer is found with the provided ID.
     */
    public CustomerDto findCustomerById(Long customerId) {
        logger.info("Retrieving customer with id: {}", customerId);
        Customer customer = repo.findById(customerId).orElseThrow(() -> {
            logger.warn("Customer profile not found for ID: {}", customerId);
            return new CustomerNotFoundException("Customer with id - " + customerId + " not found!");
        });

        return CustomerDto.builder()
                .customerId(customer.getCustomerId().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .build();
    }

    /**
     * Retrieves a customer by their username.
     * Access is restricted so that a logged-in user can only access their own data.
     *
     * @param userName the customer's username.
     * @return a detailed CustomerDto representing the found customer.
     * @throws UsernameNotFoundException if no customer is found with the provided username.
     */
    public CustomerDto findUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("Retrieving customer with username: {}", MaskingUtil.mask(userName));
        Customer customer = repo.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with username: "
                        + MaskingUtil.mask(userName) + " not found"));
        return CustomerDto.builder()
                .customerId(customer.getCustomerId().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .build();
    }

    /**
     * Authenticates a customer and generates a JWT and refresh token.
     * This method performs authentication, then retrieves a detailed {@code CustomerDto}
     * to include in the JWT response.
     *
     * @param username the customer's username.
     * @param password the customer's password.
     * @return a JwtResponse containing the JWT, refresh token, token validity, and a detailed CustomerDto.
     */
    public JwtResponse<CustomerDto> login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception ex) {
            logger.error("Authentication failed for username: {}", MaskingUtil.mask(username), ex);
            throw ex;
        }
        final UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
        final String jwtToken = jwtUtil.generateToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), userDetails);
        CustomerDto customerDto = findUserByUsername(username);
        customerDto.setPassword(null);
        return new JwtResponse<>(
                jwtToken, refreshToken, String.valueOf(jwtUtil.getTOKEN_VALIDITY()), customerDto
        );
    }
}
