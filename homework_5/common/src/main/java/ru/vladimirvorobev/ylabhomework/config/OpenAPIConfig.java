package ru.vladimirvorobev.ylabhomework.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурация OpenAPI.
 **/
@Configuration
public class OpenAPIConfig {

  @Value("${http://localhost:8080/}")
  private String devUrl;

  @Value("${http://localhost:8080/}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
      Server devServer = new Server();

      devServer.setUrl(devUrl);
      devServer.setDescription("Homework 5");

      Server prodServer = new Server();

      prodServer.setUrl(prodUrl);
      prodServer.setDescription("Homework 5");

      Contact contact = new Contact();

      contact.setName("Vladimir Vorobev");

      Info info = new Info()
          .title("Homework 5")
          .version("1.0")
          .contact(contact)
          .description("Homework 5");

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }

}
