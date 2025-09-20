package com.mscnptpm.identityservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI identityServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Identity Service API")
                        .description("APIs for managing Users & Authentication in E-commerce system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("E-commerce Security Team")
                                .email("security@ecommerce.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Identity Service Wiki")
                        .url("https://github.com/your-org/ecommerce/identity-service"));
    }
}
