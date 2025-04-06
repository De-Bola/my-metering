//package com.enefit.metering.service;
//
//import com.enefit.metering.service.AbstractIntegrationTest;
//import com.enefit.metering.TestcontainersConfiguration;
//import com.enefit.metering.models.Customer;
//import com.enefit.metering.models.MeteringPoint;
//import com.enefit.metering.repository.CustomerRepository;
//import com.enefit.metering.repository.MeteringPointsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Import(TestcontainersConfiguration.class)
//@Testcontainers
//public class MeteringPointServiceIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    private MeteringPointService meteringPointService;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private MeteringPointsRepository meteringPointsRepository;
//
//    private Customer testCustomer;
//
//    @BeforeEach
//    public void setup() {
//        meteringPointsRepository.deleteAll();
//        customerRepository.deleteAll();
//
//        // Create a test customer.
//        testCustomer = new Customer();
//        testCustomer.setUsername("mp_test@example.com");
//        testCustomer.setPassword("encrypted");
//        testCustomer.setFirstName("Test");
//        testCustomer.setLastName("Customer");
//        testCustomer = customerRepository.save(testCustomer);
//    }
//
//    @Test
//    public void testAddMeteringPoint_success() {
//        String address = "123 Service Street";
//        MeteringPoint mp = meteringPointService.addMeteringPoint(address);
//        assertNotNull(mp.getMeteringPointId());
//        assertEquals(address, mp.getAddress());
//    }
//
//    @Test
//    public void testFindCustomerMeteringPoints_success() {
//        // Create two metering points.
//        meteringPointService.addMeteringPoint("Address 1");
//        meteringPointService.addMeteringPoint("Address 2");
//
//        List<MeteringPoint> points = meteringPointService.findCustomerMeteringPoints();
//        assertNotNull(points);
//        assertTrue(points.size() >= 2);
//    }
//}
