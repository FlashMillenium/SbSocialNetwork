package ru.sberbank.gqw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.gqw.controllers.MessageController;
import ru.sberbank.gqw.entity.Message;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@EnableCircuitBreaker
public class MessageServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceApplication.class);

    private MessageController messageController;

    @Autowired
    public void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

    @RequestMapping("get/message")
    public List<Message> getTestFromDB(){
        return messageController.getAll();
    }




}
