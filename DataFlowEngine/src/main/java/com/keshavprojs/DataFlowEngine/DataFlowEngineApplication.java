package com.keshavprojs.DataFlowEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataFlowEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataFlowEngineApplication.class, args);
		System.out.println("Spring Boot Data Pipeline Application Started!");
		System.out.println("Data Generator and Processor are running in the background...");
		System.out.println("Check console output for generated and processed data.");
	}
}
