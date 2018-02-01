package com.tree.springcloud.bl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TreeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreeApiApplication.class, args);
	}
}
