package com.team2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CaffeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeApplication.class, args);
	}

}
