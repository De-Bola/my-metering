package com.enefit.metering.controller;

import com.enefit.metering.config.TestContainerInitializer;
import com.enefit.metering.controller.response.SuccessResponse;
import com.enefit.metering.models.CustomerDto;
import com.enefit.metering.models.JwtRequest;
import com.enefit.metering.models.JwtResponse;
import com.enefit.metering.repository.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(TestContainerInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestContainerInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest {

    public static final String BASE_URL = "http://localhost:";
    @Autowired
    private static TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private String token = null;

    private CustomerDto testCustomer;

    private Long customerId;

    @LocalServerPort
    private int port;

    private URI uri;
    private LocalDateTime lastLogin;
    private HttpHeaders headers;
    HttpEntity<String> entity;

    @Value("${token.validity}")
    private long TOKEN_VALIDITY;

    @Autowired
    ObjectMapper mapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @BeforeAll
    static void init(){
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setup() throws URISyntaxException, JsonProcessingException {
        customerRepository.deleteAll();

        testCustomer = new CustomerDto("Test", "User", "testuser@example.com", "encrypted");

        uri = new URI(BASE_URL + port + "/api/auth/register");

        ResponseEntity<SuccessResponse> responseEntity = restTemplate
                .postForEntity(uri, testCustomer, SuccessResponse.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        boolean isExpired = lastLogin == null || lastLogin.isBefore(LocalDateTime.now().minusSeconds(TOKEN_VALIDITY / 1000));
        if (token == null || isExpired) {
            uri = new URI(BASE_URL + port + "/api/auth/login");
            JwtRequest jwtRequest = new JwtRequest(testCustomer.getUsername(), testCustomer.getPassword());
            responseEntity = restTemplate
                    .postForEntity(uri, jwtRequest, SuccessResponse.class);
            lastLogin = LocalDateTime.now();
        }

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isNotNull();
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());

        Object createdCustomer = responseEntity.getBody().getData();
        String jsonStr = mapper.writeValueAsString(createdCustomer); // write first
        JwtResponse<?> jwtResponse = mapper.readValue(jsonStr, JwtResponse.class); // then read
        token = jwtResponse.getToken();
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        entity = new HttpEntity<>(headers);
        Object loggedInCustomer = jwtResponse.getResponse();
        String jsonResult = mapper.writeValueAsString(loggedInCustomer);
        CustomerDto customerDto = mapper.readValue(jsonResult, CustomerDto.class);
        customerId = Long.valueOf(customerDto.getCustomerId());
    }

    @Test
    public void testGetCustomerById() throws JsonProcessingException {
        String url = BASE_URL + port + "/api/customers/" + customerId;

        ResponseEntity<SuccessResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuccessResponse<?> body = response.getBody();
        assertNotNull(body);

        String jsonResult = mapper.writeValueAsString(body.getData());
        CustomerDto customerDto = mapper.readValue(jsonResult, CustomerDto.class);
        assertEquals(customerId, Long.valueOf(customerDto.getCustomerId()));
        assertEquals(testCustomer.getUsername(), customerDto.getUsername());
    }

    @Test
    public void testGetLoggedInCustomer() throws JsonProcessingException {
        String url = BASE_URL + port + "/api/customers/me";

        ResponseEntity<SuccessResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SuccessResponse<?> body = response.getBody();
        assertNotNull(body);
        String jsonResult = mapper.writeValueAsString(body.getData());
        CustomerDto customerDto = mapper.readValue(jsonResult, CustomerDto.class);
        assertEquals(customerId, Long.valueOf(customerDto.getCustomerId()));
        assertEquals(testCustomer.getUsername(), customerDto.getUsername());
    }
}
