package com.aharon.shared.configuration;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration

public class OpenApiConfiguration {
    public OpenAPI customOpenApi(
            String applicationDescription,
            String applicationVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Aharon API")
                        .version(applicationVersion)
                        .description(applicationDescription)
                        .license(new License().name("Apache 2.0 License").url("https://Aharon.com/license"))
                        .contact(new Contact()
                                .url("https://Aharon.com")
                                .name("Aharon")));
    }
}
