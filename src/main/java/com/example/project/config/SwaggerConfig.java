package com.example.project.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableWebMvc
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())  // request response에 대한 설정
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.project.controller")) // Swagger API 문서로 만들기 원하는 BasePackage 경로
                .paths(PathSelectors.any()) // URL 경로를 지정하여 해당 URL에 해당하는 요청만 Swagger API 문서
                .build()
                .apiInfo(apiInfo());
    }
    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }
    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Documentation")
                .description("swagger config")
                .version("1.0")
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}
