package com.drm.mitra.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
//@ConfigurationProperties("")
public class GoogleClientConfigProperties {
    private String googleClientId;
    private String googleClientSecret;
}
