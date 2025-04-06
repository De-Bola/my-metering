//package com.enefit.metering.controller;
//
//import com.enefit.metering.service.AbstractIntegrationTest;
//import com.enefit.metering.controller.response.SuccessResponse;
//import com.enefit.metering.models.Consumption;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ConsumptionControllerIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private final Long meteringPointId = 1L;
//
//    @Test
//    public void testAddAndRetrieveConsumptions() {
//        Consumption consumption = new Consumption();
//        consumption.setAmount(new BigDecimal("12.3").doubleValue());
//        consumption.setAmountUnit("kWh");
//        consumption.setConsumptionTime(LocalDateTime.now());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<?> requestEntity = new HttpEntity<>(Collections.singletonList(consumption), headers);
//
//        ResponseEntity<SuccessResponse> postResponse = restTemplate.exchange(
//                "/api/metering-points/" + meteringPointId + "/consumptions",
//                HttpMethod.POST,
//                requestEntity,
//                SuccessResponse.class
//        );
//        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
//        assertNotNull(postResponse.getBody());
//        int savedCount = Integer.parseInt((String) postResponse.getBody().getData());
//        assertTrue(savedCount > 0);
//
//        ResponseEntity<SuccessResponse> getResponse = restTemplate.exchange(
//                "/api/metering-points/" + meteringPointId + "/consumptions",
//                HttpMethod.GET,
//                null,
//                SuccessResponse.class
//        );
//        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
//        assertNotNull(getResponse.getBody());
//        // You can assert on the size of the returned data if you expect a specific count.
//    }
//}
