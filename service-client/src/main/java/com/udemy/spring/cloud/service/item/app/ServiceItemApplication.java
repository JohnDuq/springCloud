package com.udemy.spring.cloud.service.item.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
// import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableEurekaClient
// @RibbonClient(name = "service-producer")
@EnableFeignClients
@SpringBootApplication
public class ServiceItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceItemApplication.class, args);
	}

}
