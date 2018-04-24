package ru.sberbank.front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.front.models.User;
import ru.sberbank.gqw.dto.UserDTO;



@Service
public class UsersLoginService {
    @Autowired
    private UserLogin userLogin;
    User user = new User();

    @GetMapping("/users/login")
    public ResponseEntity<UserDTO> getLoginFromMicroserv(String login) {
        ResponseEntity<UserDTO> response = userLogin.getByLogin(login);
        user.setUserDTO(response.getBody());
        return response;
    }
}
