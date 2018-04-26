package ru.sberbank.front.services.friends;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.front.services.RestResponsePage;
import ru.sberbank.gqw.dto.UserDTO;


@FeignClient(name = "user-service")
public interface FriendsList {
    @GetMapping("/users/{login}")
    ResponseEntity<UserDTO> getByLogin(@PathVariable(value = "login") String login);

    @PostMapping("/users/{id}/friends")
    RestResponsePage<UserDTO> getFriends(@PathVariable(value = "id") Long id, @PageableDefault Pageable pageable);
}
