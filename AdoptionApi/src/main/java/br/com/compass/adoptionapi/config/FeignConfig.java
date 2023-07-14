package br.com.compass.adoptionapi.config;

import feign.Client;
import feign.Contract;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {
  @Bean
  public Client feignClient() {
    return new OkHttpClient();
  }
}
