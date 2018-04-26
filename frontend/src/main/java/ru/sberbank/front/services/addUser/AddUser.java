package ru.sberbank.front.services.addUser;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.gqw.dto.UserDTO;


@FeignClient(name = "user-service")
public interface AddUser {
    @GetMapping("/users/{login}")
    ResponseEntity<UserDTO> getByLogin(@PathVariable(value = "login") String login);

    @PutMapping("/users/{id}/friends")
    public ResponseEntity<?> addFriend(@PathVariable(value = "id") Long id, @RequestBody Long friendId);


}
