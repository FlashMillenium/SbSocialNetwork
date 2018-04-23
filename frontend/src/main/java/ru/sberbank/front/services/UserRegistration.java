package ru.sberbank.front.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.gqw.dto.UserDTO;


@FeignClient(name = "user-service")
public interface UserRegistration {
    @PostMapping("/users/add")
    ResponseEntity<UserDTO> addUser(@RequestBody UserDTO newUser);
}
