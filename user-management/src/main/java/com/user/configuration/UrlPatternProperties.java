package com.user.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "url") 
@PropertySource("classpath:url.properties")
@Data
public class UrlPatternProperties {
	
	private String superadmin;
	
	private String admin;
	
	private String user;

}
