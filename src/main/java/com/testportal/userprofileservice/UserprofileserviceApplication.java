package com.testportal.userprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = { "com.testportal.testportal.commonframework.security", "com.testportal.testportal.commonframework.bean",
		"com.testportal.testportal.commonframework.controller", "com.testportal.testportal.commonframework.exceptions", "com.testportal.userprofileservice" })
public class UserprofileserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserprofileserviceApplication.class, args);
	}

}
