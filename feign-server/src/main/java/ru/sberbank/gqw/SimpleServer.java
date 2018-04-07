package ru.sberbank.gqw;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SimpleServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    private List<SimpleDto> list =Arrays.asList(new SimpleDto(66L, "sixty six"), new SimpleDto(42L, "answer on everythong") );

    public static void main(String[] args) {
        SpringApplication.run(SimpleServer.class, args);
    }

    @RequestMapping("get/{id}")
    public SimpleDto getById(@PathVariable Long id) {
        logger.debug("call method with id: " + id);
        return list.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new SimpleDto(404L, "error"));
    }
}
