package com.connekt.security.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${spring.application.name}")
  private String audioBook;
  @Bean
  public Docket api() {
    HashSet<String> consumesAndProduces =
      new HashSet<>(Arrays.asList("application/json"));

    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .consumes(consumesAndProduces)
      .produces(consumesAndProduces)
      .pathMapping("/")
      .globalOperationParameters(
        Arrays.asList(new ParameterBuilder()
          .name("Authorization")
          .defaultValue("Bearer ")
          .description("Security token")
          .modelRef(new ModelRef("string"))
          .parameterType("header")
          .required(true)
          .build()))
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(Predicates.not(PathSelectors.regex("/error.*")))
      .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
      "AUDIO BOOK REST API",
      "Some custom description of API.",
      "0.1.0",
      "Terms of service",
      new Contact("Ngouleu&Serkwi", "www.audiobook.com", "ngouleutert1@gmail.com"),
      "License of API", "API license URL", Collections.emptyList());
  }
}
