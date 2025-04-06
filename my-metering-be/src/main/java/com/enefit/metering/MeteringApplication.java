package com.enefit.metering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MeteringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteringApplication.class, args);
	}
	//uri -> http://localhost:8080/swagger-ui.html

}
