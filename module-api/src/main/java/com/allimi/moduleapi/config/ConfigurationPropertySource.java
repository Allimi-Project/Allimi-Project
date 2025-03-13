package com.allimi.moduleapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigurationPropertySource {

	@Value("${spring.open-api.serviceKey}")
	private String serviceKey;

	@Value("${spring.open-api.url}")
	private String url;
}