package com.udemy.spring.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.udemy.spring.cloud.commons.model.auth" })
public class ServiceUserRoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserRoleApplication.class, args);
	}

}
