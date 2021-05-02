package com.navigation.geocodingapi.infrastructure.config;

import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .paths(Predicate.not(PathSelectors.regex("/error.*")))
        .paths(Predicate.not(PathSelectors.regex("/actuator.*")))
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(metadata());
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
        .title("Geocoding")
        .description("Geocoding Service REST API")
        .build();
  }
}
