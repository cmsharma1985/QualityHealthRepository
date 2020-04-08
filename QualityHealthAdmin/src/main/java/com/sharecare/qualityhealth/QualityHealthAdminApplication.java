package com.sharecare.qualityhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class QualityHealthAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(QualityHealthAdminApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QualityHealthAdminApplication.class);
	}
}
