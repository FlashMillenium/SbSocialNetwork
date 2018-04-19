package ru.sberbank.front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.gqw.dto.UserDTO;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 19.04.2018
 */

@Service
public class UserRegistrationTest {
    @Autowired
    private UserRegistration usrReg;
    private UserDTO userDTO = new UserDTO();

    @PostMapping("/users/add")
    public ResponseEntity<UserDTO> getRegistrFromMicroserv(String passwd, String login) {
        userDTO.setPassword(passwd);
        userDTO.setLogin(login);
        return usrReg.addUser(userDTO);
    }
}
