package com.example.waterlogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterloggingApplication.class, args);
		System.out.println("<<< Waterlogging Backend Started >>>");
		System.out.println("<<< Remember to enable PostGIS in your PostgreSQL DB: CREATE EXTENSION postgis; >>>");
	}

}


