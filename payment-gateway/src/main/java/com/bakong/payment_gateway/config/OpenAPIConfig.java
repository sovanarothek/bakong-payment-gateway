package com.bakong.payment_gateway.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenAPIConfig {

    @Value("${my-config.openapi.dev-url}")
    private String devUrl;

    @Value("${my-config.openapi.prod-url}")
    private String prodUrl;

    @Value("${my-config.openapi.local-url}")
    private String localUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl(localUrl);
        localServer.setDescription("Local Server URL in Development environment");

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("DEV Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("PROD Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("limchansamnang@gmail.com");
        contact.setName("Samnang ( Software Engineer )");
        contact.setUrl("https://github.com/Chansamnang");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Bakong Payment Gateway")
                .version("1.0")
                .contact(contact)
                .description("For learning purpose how bakong system work").termsOfService("https://www.jenny.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localServer, devServer, prodServer));
    }
}
