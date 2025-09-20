package com.mscnptpm.paymentservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Payment Service API",
                version = "1.0",
                description = "API cho xử lý thanh toán trong hệ thống e-commerce",
                contact = @Contact(
                        name = "Payment Team",
                        email = "support@ecommerce.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8084/payment-service", description = "Local Dev Server")
        }
)
public class SwaggerConfig {
}
