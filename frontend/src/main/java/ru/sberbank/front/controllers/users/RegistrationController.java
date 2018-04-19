package ru.sberbank.front.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.front.services.UserRegistrationTest;


@Controller
public class RegistrationController {
    UserRegistrationTest userRegistration = new UserRegistrationTest();
    @RequestMapping(value = "users/registration")
    public String registrPage() {
        userRegistration.getRegistrFromMicroserv("aaa", "bbb");
        return "users/profile";
    }
}
