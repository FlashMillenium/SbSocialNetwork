package ru.sberbank.gqw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.gqw.dto.UserDTO;
import ru.sberbank.gqw.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDTO> getByLogin(@PathVariable(value = "login") String login) {
        return userService.getByLogin(login);
    }

    @GetMapping("/users/{id}/friends")
    public Page<UserDTO>  getFriends(@PathVariable(value = "id") Long id, @PageableDefault Pageable pageable) {
        return userService.getFriends(id, pageable);
    }

    @PostMapping("/users/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO newUser) {
        return userService.addUser(newUser);
    }

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO updateUser) {
        return null;
    }

    @DeleteMapping("/users/{id}/friends")
    public ResponseEntity<?> deleteFriend(@PathVariable(value = "id") Long id, @RequestBody Long friendId) {
        return null;
    }

    @PutMapping("/users/{id}/friends")
    public ResponseEntity<?> addFriend(@PathVariable(value = "id") Long id, @RequestBody  Long friendId) {
        return null;
    }


}
