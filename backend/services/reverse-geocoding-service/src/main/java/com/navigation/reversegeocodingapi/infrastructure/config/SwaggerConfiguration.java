package com.navigation.reversegeocodingapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Reverse Geocoding")
                .description("Reverse Geocoding REST API")
                .version("v0.0.1")
                .license(new License().name("MIT")));
  }
}
