package com.user.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:application.properties")
@Data
public class ApplicationProperties {

	private String dateFormat;

	private String emailPattern;

	private String aadharPattern;

}
