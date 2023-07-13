//package io.github.felipesilva15.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("io.github.felipesilva15.api.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .apiInfo(apiInfo());
//    }
//
//    private Contact contact() {
//        return new Contact("Felipe Silva", "https://github.com/felipesilva15", "felipe.allware@gmail.com");
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Vendas Spring Boot API")
//                .description("API em Spring Boot do projeto de Vendas")
//                .version("1.0")
//                .contact(contact())
//                .build();
//    }
//
//    public ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//
//        AuthorizationScope[] scopes = new AuthorizationScope[1];
//        scopes[0] = authorizationScope;
//        SecurityReference reference = new SecurityReference("JWT", scopes);
//        List<SecurityReference> auths = new ArrayList<>();
//
//        auths.add(reference);
//
//        return auths;
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext
//                .builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.any())
//                .build();
//    }
//}
