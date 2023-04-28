package com.testportal.userprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserprofileserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserprofileserviceApplication.class, args);
	}

}
