package com.example.newyorkbabies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NewYorkBabiesApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewYorkBabiesApplication.class, args);
	}
}