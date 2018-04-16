package ru.sberbank.front.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 11.04.2018
 */

@Controller
public class ProfileController {
    @RequestMapping("/users/profile")
    public String profile() {
        return "users/profile";
    }
}
