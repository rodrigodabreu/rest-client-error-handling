package dev.rodrigo.abreu.restclienterrorhandling;

import dev.rodrigo.abreu.restclienterrorhandling.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class HttpBinController {

  private static final Logger log = LoggerFactory.getLogger(HttpBinController.class);

  private final RestClient client;

  public HttpBinController(RestClient client) {
    this.client = client;
  }

  @GetMapping("/get")
  public String get() {
    return client.get()
        .uri("/get")
        .retrieve()
        .body(String.class);
  }

  @GetMapping("/get/status/{code}")
  public ResponseEntity<String> getStatusCode(@PathVariable Integer code) {
    ResponseEntity<Void> response = client.get()
        .uri("/status/{code}", code)
        .retrieve()
        .toBodilessEntity();

    return ResponseEntity.ok("Success - StatusCode: " + response.getStatusCode().value());
  }

  @Retryable(includes = ApiException.class, maxRetries = 3, delay = 1000L, multiplier = 2)
  @GetMapping("/get/unstable")
  public String getUnstable(){
    log.info("Attempting to get unstable resource");
    return client.get()
        .uri("/unstable")
        .retrieve()
        .body(String.class);
  }

}
