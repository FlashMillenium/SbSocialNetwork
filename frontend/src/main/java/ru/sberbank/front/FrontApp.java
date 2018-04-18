package ru.sberbank.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FrontApp {
    public static void main(String[] args) {
        SpringApplication.run(FrontApp.class, args);
    }
}

