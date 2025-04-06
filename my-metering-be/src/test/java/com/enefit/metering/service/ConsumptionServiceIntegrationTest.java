//package com.enefit.metering.service;
//
//import com.enefit.metering.exceptions.MeteringPointNotFoundException;
//import com.enefit.metering.models.Consumption;
//import com.enefit.metering.models.Customer;
//import com.enefit.metering.models.MeteringPoint;
//import com.enefit.metering.repository.ConsumptionRepository;
//import com.enefit.metering.repository.CustomerRepository;
//import com.enefit.metering.repository.MeteringPointsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ConsumptionServiceIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private ConsumptionService consumptionService;
//
//    @Autowired
//    private MeteringPointsRepository meteringPointsRepository;
////
//    @Autowired
//    private ConsumptionRepository consumptionRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private SecurityService securityService;
//
//    private Customer testCustomer;
//    private MeteringPoint testMeteringPoint;
//
//    @BeforeEach
//    public void setup() {
//        testCustomer = new Customer();
//        testCustomer.setUsername("testuser");
//        testCustomer.setPassword("encrypted"); // assume already encrypted for tests
//        testCustomer.setFirstName("Test");
//        testCustomer.setLastName("User");
//        testCustomer = customerRepository.save(testCustomer);
//
//        testMeteringPoint = new MeteringPoint();
//        testMeteringPoint.setAddress("123 Test Street");
//        testMeteringPoint.setCustomer(testCustomer);
//        testMeteringPoint = meteringPointsRepository.save(testMeteringPoint);
//
//        // Optionally, if your SecurityService is not mocked, ensure it returns "testuser".
//        // This could be achieved with a @MockBean in a real test, or by configuring a test SecurityContext.
//    }
//
//    @Test
//    public void testAddConsumption_success() {
//        Consumption consumption = new Consumption();
//        consumption.setAmount(new BigDecimal("10.5").doubleValue());
//        consumption.setAmountUnit("kWh");
//        consumption.setConsumptionTime(LocalDateTime.now());
//
//        int count = consumptionService.addConsumption(Collections.singletonList(consumption),
//                testMeteringPoint.getMeteringPointId());
//        assertEquals(1, count);
//
//        List<Consumption> consumptions = consumptionRepository.findByMeteringPointMeteringPointId(testMeteringPoint.getMeteringPointId());
//        assertFalse(consumptions.isEmpty());
//        assertEquals(new BigDecimal("10.5"), consumptions.get(0).getAmount());
//    }
//
//    @Test
//    public void testAddConsumption_meteringPointNotFound() {
//        Consumption consumption = new Consumption();
//        consumption.setAmount(new BigDecimal("5.0").doubleValue());
//        consumption.setAmountUnit("kWh");
//        consumption.setConsumptionTime(LocalDateTime.now());
//
//        Exception exception = assertThrows(MeteringPointNotFoundException.class,
//                () -> consumptionService.addConsumption(Collections.singletonList(consumption), 9999L));
//        assertTrue(exception.getMessage().contains("not found"));
//    }
//}
