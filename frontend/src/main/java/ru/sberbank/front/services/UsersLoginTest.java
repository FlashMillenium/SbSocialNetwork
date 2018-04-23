package ru.sberbank.front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.gqw.dto.UserDTO;



@Service
public class UsersLoginTest {
    @Autowired
    private UserLogin userLogin;


    @GetMapping("/users/login")
    public ResponseEntity<UserDTO> getLoginFromMicroserv(String login) {
        ResponseEntity<UserDTO> response = userLogin.getByLogin(login);
        return response;
    }
}
