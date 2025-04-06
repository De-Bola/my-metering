//package com.enefit.metering.service;
//
//import com.enefit.metering.AbstractIntegrationTest;
//import com.enefit.metering.TestcontainersConfiguration;
//import com.enefit.metering.models.MarketPriceDataPoint;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.OffsetDateTime;
//
//@SpringBootTest
//@Import(TestcontainersConfiguration.class)
//@Testcontainers
//public class CostServiceIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setup() {
//    }
//
//    @Test
//    public void testJsonSerializationReturnsJsonResult() {
//        MarketPriceDataPoint sample = new MarketPriceDataPoint(
//                10.0, 12.0, 100.0, 120.0, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1)
//        );
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
//        byte[] bytes = serializer.serialize(sample);
//        String json = new String(bytes);
//        System.out.println("Serialized JSON: " + json);
//
//    }
//}
