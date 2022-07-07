package com.drm.mitra.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class ConfigProperties {
    private String accountSId;
    private String authId;
    private String trailNumber;
    private String serviceId;
}
