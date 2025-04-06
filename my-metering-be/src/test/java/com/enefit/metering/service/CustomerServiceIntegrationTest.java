//package com.enefit.metering.service;
//
//import com.enefit.metering.service.AbstractIntegrationTest;
//import com.enefit.metering.TestcontainersConfiguration;
//import com.enefit.metering.exceptions.CustomerAlreadyExistsException;
//import com.enefit.metering.models.CustomerDto;
//import com.enefit.metering.models.JwtResponse;
//import com.enefit.metering.repository.CustomerRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Import(TestcontainersConfiguration.class)
//@Testcontainers
//public class CustomerServiceIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @BeforeEach
//    public void setup() {
//        customerRepository.deleteAll();
//    }
//
//    @Test
//    public void testCreateCustomer_success() throws CustomerAlreadyExistsException {
//        CustomerDto dto = new CustomerDto();
//        dto.setFirstName("Alice");
//        dto.setLastName("Wonderland");
//        dto.setUsername("alice@example.com");
//        dto.setPassword("password");
//
//        CustomerDto saved = customerService.createCustomer(dto);
//        assertNotNull(saved.getCustomerId());
//        assertEquals("Alice", saved.getFirstName());
//    }
//
//    @Test
//    public void testFindCustomerById_success() throws CustomerAlreadyExistsException {
//        CustomerDto dto = new CustomerDto();
//        dto.setFirstName("Bob");
//        dto.setLastName("Builder");
//        dto.setUsername("bob@example.com");
//        dto.setPassword("password");
//        CustomerDto saved = customerService.createCustomer(dto);
//
//        CustomerDto fetched = customerService.findCustomerById(Long.parseLong(saved.getCustomerId()));
//        assertEquals(saved.getCustomerId(), fetched.getCustomerId());
//    }
//
//    @Test
//    public void testFindUserByUsername_success() throws CustomerAlreadyExistsException {
//        CustomerDto dto = new CustomerDto();
//        dto.setFirstName("Charlie");
//        dto.setLastName("Chaplin");
//        dto.setUsername("charlie@example.com");
//        dto.setPassword("password");
//        customerService.createCustomer(dto);
//
//        CustomerDto fetched = customerService.findUserByUsername("charlie@example.com");
//        assertNotNull(fetched);
//        assertEquals("charlie@example.com", fetched.getUsername());
//    }
//
//    @Test
//    public void testLogin_success() throws CustomerAlreadyExistsException {
//        CustomerDto dto = new CustomerDto();
//        dto.setFirstName("Dora");
//        dto.setLastName("Explorer");
//        dto.setUsername("dora@example.com");
//        dto.setPassword("password");
//        customerService.createCustomer(dto);
//
//        JwtResponse<CustomerDto> jwtResponse = customerService.login("dora@example.com", "password");
//        assertNotNull(jwtResponse.getToken());
//        assertNotNull(jwtResponse.getRefreshToken());
//    }
//}
