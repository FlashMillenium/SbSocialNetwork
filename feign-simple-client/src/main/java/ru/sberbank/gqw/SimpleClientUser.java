package ru.sberbank.gqw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class SimpleClientUser {

    @Autowired
    private SimpleFeignClient client;

    public static void main(String[] args) {
        SpringApplication.run(SimpleClientUser.class, args);
    }

    @RequestMapping("get/{id}")
    public SimpleDto getFromMicroservice(@PathVariable Long id){
        return client.getById(id);
    }
}
