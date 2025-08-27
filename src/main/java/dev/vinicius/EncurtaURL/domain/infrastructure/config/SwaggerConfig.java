package dev.vinicius.EncurtaURL.domain.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("CurtURL")
                        .description("Documentação do projeto de encurtamento de URL e geração de QR Code.")
                        .termsOfService("Termos em desenvolvimento...")
                        .version("1.0")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
        );
    }

}
