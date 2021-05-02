package com.navigation.pathfindingapi.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfiguration {
  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Pathfinding")
                .description("Pathfinding Service REST API")
                .version("v0.0.1")
                .license(new License().name("MIT")));
  }
}
