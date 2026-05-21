package com.example.mgsc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MgscApplication {

	private static final Logger logger = LoggerFactory.getLogger(MgscApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MgscApplication.class, args);
		Environment env = context.getEnvironment();
		logger.info("Perfiles activos: {}", (Object) env.getActiveProfiles());
	}

}
