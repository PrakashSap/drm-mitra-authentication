package com.drm.mitra.authentication;

import com.drm.mitra.authentication.config.ConfigProperties;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebMvc
@EnableEurekaClient
public class DrmMitraAuthenticationApplication {

	@Autowired
	private ConfigProperties configProperties;

	@PostConstruct
	public void initializeTwilio() {
		Twilio.init(configProperties.getAccountSId(),configProperties.getAuthId());
	}

	public static void main(String[] args) {
		SpringApplication.run(DrmMitraAuthenticationApplication.class, args);
		System.out.println("Welcome to DRM-MITRA");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}



}
