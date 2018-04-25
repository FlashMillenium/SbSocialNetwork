package ru.sberbank.front.services.registr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.gqw.dto.UserDTO;



@Service
public class UserRegistrationService {
    @Autowired
    private UserRegistration usrReg;
    private UserDTO userDTO = new UserDTO();

    @PostMapping("/users/add")
    public ResponseEntity<UserDTO> getRegistrFromMicroserv(String passwd, String login) {
        userDTO.setPassword(passwd);
        userDTO.setLogin(login);
        ResponseEntity<UserDTO> response = usrReg.addUser(userDTO);
        return response;
    }
}
