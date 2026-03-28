package dev.rodrigo.abreu.restclienterrorhandling;

import dev.rodrigo.abreu.restclienterrorhandling.exceptions.ApiException;
import dev.rodrigo.abreu.restclienterrorhandling.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Value("${httpbin.base-url}")
  private String baseUrl;

  @Bean
  public RestClient httpBinClient(RestClient.Builder builder) {
    return builder
        .baseUrl("https://httpbin.org/")
        .defaultStatusHandler(HttpStatusCode::isError, (HttpRequest request, ClientHttpResponse response) -> {
          if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("Resource not found");
          }
          throw new ApiException("Unexpected error", response.getStatusCode());
        })
        .build();
  }

}
