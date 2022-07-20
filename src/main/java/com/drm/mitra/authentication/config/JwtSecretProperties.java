package com.drm.mitra.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class JwtSecretProperties {
    private String secret;
    private String expiration_time;
}
