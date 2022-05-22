package com.tutorial.crud.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;

/** @author diegotobalina created on 24/06/2020 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

  private String basePackage = "com.tutorial.crud";

  @Bean
  public Docket eDesignApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .ignoredParameterTypes(Principal.class)
        .enable(true)
        .select()
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .directModelSubstitute(LocalDate.class, String.class)
        .globalOperationParameters(
            Arrays.asList(
                new ParameterBuilder()
                    .name("Authorization")
                    .description("Access_token")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false) //Para que no sea requerido el token
                    // Eliminar este defaultValue en PRE
                    .defaultValue(
                        "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0b2tlbkJvYXJkaW5nSldUIiwiaXNzIjoiYm9hcmRpbmdAaGF5YS5jb20iLCJzdWIiOiJ1c3JfcHJlY2Z2MUBoYXlhcHJlLmVzIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9HRVNUT1IiXSwiaWF0IjoxNjA3OTM1MzQ4LCJleHAiOjE2NDA5MDUyMDB9.26ca7jd9Eqceb8tU1FM_g_Sw33i3Mv782vbaYabp16QEfgb-PvDCq1WuVsQUWNMhn9PvDf3IEe90Jwq9PTCkUw")
                    .build()))
        .genericModelSubstitutes(ResponseEntity.class)
        .useDefaultResponseMessages(false)
        .enableUrlTemplating(false);
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .deepLinking(false)
        .displayOperationId(Boolean.FALSE)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(ModelRendering.EXAMPLE)
        .displayRequestDuration(true)
        .docExpansion(DocExpansion.NONE)
        .filter(false)
        .maxDisplayedTags(0)
        .operationsSorter(OperationsSorter.ALPHA)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new CommonsMultipartResolver();
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .title("Boarding API")
        .description("Boarding API")
        .version("v0")
        .build();
  }
}
