package com.enefit.metering;

import com.enefit.metering.config.TestContainerInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestMeteringApplication {

	public static void main(String[] args) {
		SpringApplication.from(MeteringApplication::main).with(TestContainerInitializer.class).run(args);
	}

}
