package com.example.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.products.repositories")
@EntityScan(basePackages = "com.example.products.entities")
public class ProductsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductsApiApplication.class, args);
	}
}