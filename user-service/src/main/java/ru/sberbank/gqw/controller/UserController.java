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

    @PostMapping("/users/{id}/friends")
    public Page<UserDTO>  getFriends(@PathVariable(value = "id") Long id, @PageableDefault Pageable pageable) {
        return userService.getFriends(id, pageable);
    }

    @PostMapping("/friends/{userLogin}")
    public Page<UserDTO>  getOtherUsers(@PathVariable(value = "userLogin") String userLogin, @PageableDefault Pageable pageable) {
        return userService.getOtherUsers(userLogin, pageable);
    }

    @PostMapping("/users/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO newUser) {
        return userService.addUser(newUser);
    }

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO updateUser) {
        return userService.updateUser(updateUser);
    }

    @DeleteMapping("/users/{id}/friends")
    public ResponseEntity<?> deleteFriend(@PathVariable(value = "id") Long id, @RequestBody Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @PutMapping("/users/{id}/friends")
    public ResponseEntity<?> addFriend(@PathVariable(value = "id") Long id, @RequestBody  Long friendId) {
        return userService.addFriend(id, friendId);
    }


}
