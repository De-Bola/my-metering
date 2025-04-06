//package com.enefit.metering.controller;
//
//import com.enefit.metering.config.TestContainerInitializer;
//import com.enefit.metering.controller.response.SuccessResponse;
//import com.enefit.metering.models.Customer;
//import com.enefit.metering.models.CustomerDto;
//import com.enefit.metering.repository.CustomerRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//@ExtendWith(TestContainerInitializer.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = TestContainerInitializer.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class CustomerControllerIntegrationTest {
//
//    public static final String BASE_URL = "http://localhost:";
//    @Autowired
//    private static TestRestTemplate restTemplate;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    private CustomerDto testCustomer;
//
//    private Long customerId;
//
//    @LocalServerPort
//    private int port;
//
//    private URI uri;
//
//
//    @BeforeAll
//    static void init(){
//        restTemplate = new TestRestTemplate();
//    }
//
//    @BeforeEach
//    public void setup() throws URISyntaxException, JsonProcessingException {
//        customerRepository.deleteAll();
//
//        testCustomer = new CustomerDto("3", "Test", "User", "testuser@example.com", "encrypted");
//
//        uri = new URI(BASE_URL + port + "/api/auth/register");
//
//        ResponseEntity<SuccessResponse> responseEntity = restTemplate
//                .postForEntity(uri, testCustomer, SuccessResponse.class);
//
//        assertThat(responseEntity).isNotNull();
//        assertThat(responseEntity.getBody()).isNotNull();
//        System.out.println(responseEntity.getStatusCode());
//        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(201));
//
//        Object createdCustomer = responseEntity.getBody().getData();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonStr = mapper.writeValueAsString(createdCustomer); // write first
//        CustomerDto customerDto = mapper.readValue(jsonStr, CustomerDto.class); // then read
//        customerId = Long.valueOf(customerDto.getCustomerId());
//        System.out.println(customerId);
//
//    }
//
//    @Test
//    public void testGetCustomerById() {
//        String url = BASE_URL + port + "/api/customers/1";
//        ResponseEntity<SuccessResponse> response = restTemplate.getForEntity(url, SuccessResponse.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SuccessResponse<?> body = response.getBody();
//        assertNotNull(body);
//    }
//
//    @Test
//    public void testGetLoggedInCustomer() {
//        // Use basic auth with the test customer's credentials.
//        TestRestTemplate authRestTemplate = restTemplate.withBasicAuth("testuser@example.com", "password");
//        String url = "/api/customers/me";
//        ResponseEntity<SuccessResponse> response = authRestTemplate.getForEntity(url, SuccessResponse.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SuccessResponse<?> body = response.getBody();
//        assertNotNull(body);
//        // more assert that the returned CustomerDto's username matches testCustomer.
//    }
//}
