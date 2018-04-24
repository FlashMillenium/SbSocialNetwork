package ru.sberbank.front.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import ru.sberbank.gqw.dto.UserDTO;

@FeignClient(name = "user-service")
public interface UserEdit {
    @PutMapping("/users/update")
    public ResponseEntity<UserDTO> updateUser(UserDTO updateUser);

}
