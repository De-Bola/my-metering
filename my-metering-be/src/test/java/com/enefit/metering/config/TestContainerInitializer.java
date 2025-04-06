package com.enefit.metering.config;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext>, AfterAllCallback {
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("root")
            .withInitScript("templates/metering-points.sql")
            .withInitScript("templates/consumption.sql")
            .withInitScript("templates/customers.sql")
            .withInitScript("templates/create-tables.sql")
            .withReuse(true);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        postgres.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (postgres == null) {
            return;
        }
        postgres.close();
    }
}