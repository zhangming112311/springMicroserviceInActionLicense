package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "example")
@Data
public class ServiceConfig {
	private String property;

	public String getProperty() {
		return property;
	}
}