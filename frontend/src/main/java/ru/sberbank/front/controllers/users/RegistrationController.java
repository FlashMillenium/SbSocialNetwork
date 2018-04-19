package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.front.services.UserRegistrationTest;
import ru.sberbank.gqw.dto.UserDTO;


@Controller
public class RegistrationController {
    @Autowired
    UserRegistrationTest userRegistration;

    @RequestMapping(value = "users/registration")
    public String registrPage() {
        ResponseEntity<UserDTO> registrFromMicroserv = userRegistration.getRegistrFromMicroserv("aaa", "bbb");
        System.out.println("!!!!!");
        System.out.println(registrFromMicroserv.getBody().getId());
        return "users/profile";
    }
}
