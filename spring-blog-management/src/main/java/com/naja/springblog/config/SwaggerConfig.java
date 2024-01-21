package com.naja.springblog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.naja.springblog.security.UserPrincipal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig  {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.naja.springblog.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo()).forCodeGeneration(true)
                .securitySchemes(Arrays
                        .asList(new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, SecuritySchemeIn.HEADER.name())))
                .securityContexts(Arrays.asList(securityContext())).ignoredParameterTypes(UserPrincipal.class);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(op -> op.requestMappingPattern().matches(DEFAULT_INCLUDE_PATTERN)).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Spring Blog API", "Spring Blog API", "1.0", "Terms of service",
                new Contact("naja  Mohamed", "www.naja.com", ""), "License of API", "API license URL"
                , Collections.emptyList());

}
}