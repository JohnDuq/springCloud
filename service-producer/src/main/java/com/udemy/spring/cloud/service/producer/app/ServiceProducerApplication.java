package com.udemy.spring.cloud.service.producer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProducerApplication.class, args);
	}

}
