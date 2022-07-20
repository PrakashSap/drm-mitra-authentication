package com.drm.mitra.authentication;

import com.drm.mitra.authentication.config.ConfigProperties;
import com.drm.mitra.authentication.config.JwtSecretProperties;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebMvc
@EnableEurekaClient
@EnableConfigurationProperties({ConfigProperties.class, JwtSecretProperties.class})
public class DrmMitraAuthenticationApplication {

	private static final String accountSId="AC048b959c3c88b7a8de69fe582a25fb09";
	private static final String authId="b080a05e0f1d0a28c0cf6704bcfbfdd9";

	@PostConstruct
	public void initializeTwilio() {
		Twilio.init(accountSId,authId);
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
