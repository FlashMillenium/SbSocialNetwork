package ru.sberbank.front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.gqw.dto.UserDTO;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 20.04.2018
 */

@Service
public class UsersLoginTest {
    @Autowired
    private UserLogin userLogin;


    @GetMapping("/users/login")
    public ResponseEntity<UserDTO> getLoginFromMicroserv(String login) {
        System.out.println("Begin service");
        ResponseEntity<UserDTO> response = userLogin.getByLogin(login);
        System.out.println(response.getBody().getLogin() + "ggggggggggggggggggggggggggggggggg");
        System.out.println("finish service");
        return response;
    }
}
