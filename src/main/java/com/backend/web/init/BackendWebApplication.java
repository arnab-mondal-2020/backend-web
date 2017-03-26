package com.backend.web.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.backend")
public class BackendWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendWebApplication.class, args);
  }
}
