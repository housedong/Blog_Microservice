package com.ysd.wr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@SpringBootApplication
public class YsdblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(YsdblogApplication.class, args);
	}

}
