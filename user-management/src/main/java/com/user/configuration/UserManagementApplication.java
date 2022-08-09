package com.user.configuration;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.user.util.CustomLogger;

@SpringBootApplication

@ComponentScan({ "com.user.*" })

@EntityScan("com.*.model")

@EnableJpaRepositories("com.*.repository")

public class UserManagementApplication implements CommandLineRunner {

	private Logger logger = CustomLogger.addLoggerClass(UserManagementApplication.class);

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Active profiles: " + Arrays.toString(environment.getActiveProfiles()));

	}

}
