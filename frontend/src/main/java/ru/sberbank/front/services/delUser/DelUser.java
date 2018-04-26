package ru.sberbank.front.services.delUser;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.gqw.dto.UserDTO;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 26.04.2018
 */

@FeignClient(name = "user-service")
public interface DelUser {
    @GetMapping("/users/{login}")
    ResponseEntity<UserDTO> getByLogin(@PathVariable(value = "login") String login);

    @DeleteMapping("/users/{id}/friends")
    public ResponseEntity<UserDTO> deleteFriend(@PathVariable(value = "id") Long id, @RequestBody Long friendId);
}
