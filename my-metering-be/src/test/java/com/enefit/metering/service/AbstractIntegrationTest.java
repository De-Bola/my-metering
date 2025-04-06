package com.enefit.metering.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

    // No need to declare the container here since TestcontainersConfiguration registers it as a bean.
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // These properties will be automatically set by the @ServiceConnection container bean.
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        // The following properties are provided by the container bean automatically.
        // If needed, you could override them here manually.
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
}
