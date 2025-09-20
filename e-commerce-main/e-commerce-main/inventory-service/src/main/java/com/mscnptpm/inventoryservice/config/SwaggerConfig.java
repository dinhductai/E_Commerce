package com.mscnptpm.inventoryservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Inventory Service API",
                version = "1.0",
                description = "API quản lý tồn kho sản phẩm trong hệ thống e-commerce",
                contact = @Contact(
                        name = "Inventory Team",
                        email = "support@ecommerce.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8085/inventory-service", description = "Local Dev Server")
        }
)
public class SwaggerConfig {
}
