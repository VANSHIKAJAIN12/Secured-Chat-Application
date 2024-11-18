package com.example.SecureChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
public class SecureChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureChatApplication.class, args);
	}
}