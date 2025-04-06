//package com.enefit.metering.controller;
//
//import com.enefit.metering.config.TestContainerInitializer;
//import com.enefit.metering.controller.response.SuccessResponse;
//import com.enefit.metering.models.MeteringPoint;
//import com.enefit.metering.models.Customer;
//import com.enefit.metering.repository.CustomerRepository;
//import com.enefit.metering.repository.MeteringPointsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//@ExtendWith(TestContainerInitializer.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = TestContainerInitializer.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class MeteringPointControllerIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private MeteringPointsRepository meteringPointsRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    private Customer testCustomer;
//    private MeteringPoint testMeteringPoint;
//
//    @BeforeEach
//    public void setup() {
//        meteringPointsRepository.deleteAll();
//        customerRepository.deleteAll();
//
//        testCustomer = new Customer();
//        testCustomer.setUsername("mpuser@example.com");
//        testCustomer.setPassword("encrypted");
//        testCustomer.setFirstName("Metering");
//        testCustomer.setLastName("PointUser");
//        testCustomer = customerRepository.save(testCustomer);
//
//        testMeteringPoint = new MeteringPoint();
//        testMeteringPoint.setAddress("456 Test Avenue");
//        testMeteringPoint.setCustomer(testCustomer);
//        testMeteringPoint = meteringPointsRepository.save(testMeteringPoint);
//    }
//
//    @Test
//    public void testAddMeteringPoint() {
//        String newAddress = "789 New Street";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> requestEntity = new HttpEntity<>(newAddress, headers);
//
//        // POST to create a new metering point.
//        ResponseEntity<SuccessResponse> response = restTemplate.exchange(
//                "/api/metering-points",
//                HttpMethod.POST,
//                requestEntity,
//                SuccessResponse.class
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SuccessResponse<?> responseBody = response.getBody();
//        assertNotNull(responseBody);
//        String meteringPointIdStr = (String) responseBody.getData();
//        assertNotNull(meteringPointIdStr);
//    }
//
//    @Test
//    public void testGetAuthorizedMeteringPoints() {
//        // Use basic auth credentials for the test customer.
//        TestRestTemplate authRestTemplate = restTemplate.withBasicAuth("mpuser@example.com", "password");
//
//        ResponseEntity<SuccessResponse> response = authRestTemplate.exchange(
//                "/api/metering-points",
//                HttpMethod.GET,
//                null,
//                SuccessResponse.class
//        );
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SuccessResponse<?> body = response.getBody();
//        assertNotNull(body);
//        // more assert that the returned list contains at least the test metering point.
//    }
//
//    @Test
//    public void testGetCostsForMeteringPoint() {
//        // Use basic auth credentials for the test customer.
//        TestRestTemplate authRestTemplate = restTemplate.withBasicAuth("mpuser@example.com", "password");
//        String startDateTime = "2024-01-01T00:00:00Z";
//        String endDateTime = "2024-01-31T23:59:59Z";
//        String url = "/api/metering-points/" + testMeteringPoint.getMeteringPointId() + "/costs?startDateTime=" + startDateTime + "&endDateTime=" + endDateTime;
//
//        ResponseEntity<SuccessResponse> response = authRestTemplate.getForEntity(url, SuccessResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        SuccessResponse<?> body = response.getBody();
//        assertNotNull(body);
//        // Further assertions can be made based on expected cost calculation results.
//    }
//}
