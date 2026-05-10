package com.familyhelpuae;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class FamilyHelpUAEApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilyHelpUAEApplication.class, args);
	}

}
