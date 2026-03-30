# SPRING BOOT RESTCLIENT ERROR HANDLING

## Mock Client
For mock the response from a client in this project I use https://httpbin.org/


## Tips
Using @Retryable for methods to get retry during http calls

reference: The @Retryable annotation is provided by the Spring Retry library, not the Java language itself, with annotation support introduced in Spring Retry 1.1. While older versions supported Java 8, modern Spring Retry (2.0+) and upcoming native Spring 7 Framework retry features require Java 17 or higher.
SpringSource

![img.png](img.png)

### Reference

Video referente -> https://www.youtube.com/watch?v=MuYzEZk6-zI


## What this project uses and what problems it solves ✅
This project demonstrates a small Spring Boot example focused on calling external HTTP services using the new Spring RestClient and handling errors in a clear, testable way.

- 🧰 Libraries & platform
  - Spring Boot (see `build.gradle`) with the new Spring RestClient support (spring-boot-starter-restclient).
  - Spring Web MVC for exposing HTTP endpoints.
  - Spring Retry-style behavior via `@Retryable` (used on the controller for retries).
  - Java toolchain configured in `build.gradle` (project sets a Java language level via toolchain).

- 🔗 What the code does
  - Configures a RestClient bean (`RestClientConfig`) with a base URL pointing to https://httpbin.org/ for easy local testing of HTTP status behaviors.
  - Sets a defaultStatusHandler on the client to translate error HTTP responses into custom runtime exceptions (`NotFoundException`, `ApiException`). This centralizes error handling for outgoing HTTP calls.
  - Exposes simple endpoints in `HttpBinController` to demonstrate:
    - GET /get -> simple JSON body fetch
    - GET /get/status/{code} -> demonstrates handling non-2xx responses (and mapping to problems)
    - GET /get/unstable -> demonstrates retry behavior using `@Retryable` and the custom exceptions
  - Provides a `GlobalExceptionHandler` that converts HTTP-related exceptions into Problem Detail responses (RFC 7807 style) for consistent error responses.

- 🛠 Problems solved
  - Centralized mapping of remote HTTP error statuses to application exceptions so calling code can decide retry logic or fail fast.
  - Demonstrates how to use `@Retryable` to automatically retry transient errors (the controller retries when `ApiException` is thrown).
  - Shows a clean way to return Problem Details to API clients via `GlobalExceptionHandler` for more informative error messages.

- 🧩 Files of interest (quick map)
  - `src/main/java/.../RestClientConfig.java` -> RestClient bean configuration and defaultStatusHandler
  - `src/main/java/.../HttpBinController.java` -> Example endpoints and usage of the RestClient + @Retryable
  - `src/main/java/.../exceptions/ApiException.java` & `NotFoundException.java` -> Domain exceptions for REST errors
  - `src/main/java/.../GlobalExceptionHandler.java` -> Maps exception to ProblemDetail responses

- 📌 Notes & environment
  - The project uses the Java toolchain configuration in `build.gradle`. Update your local JDK to match the configured languageVersion if needed.
  - The examples call https://httpbin.org/ to simulate different HTTP behaviors (status codes, unstable endpoints, etc.).

- 🙏 Attribution
  - Tips and inspiration referenced in this project come from Dan Vega — credit to him for the patterns and video walkthroughs that influenced this example.
