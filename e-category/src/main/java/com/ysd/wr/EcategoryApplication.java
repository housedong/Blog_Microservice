package com.ysd.wr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcategoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcategoryApplication.class, args);
	}

}
