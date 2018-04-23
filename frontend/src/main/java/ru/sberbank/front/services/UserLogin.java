package ru.sberbank.front.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sberbank.gqw.dto.UserDTO;

@FeignClient(name = "user-service")
public interface UserLogin {
    @GetMapping("/users/{login}")
    ResponseEntity<UserDTO> getByLogin(@PathVariable(value = "login") String login);
}
