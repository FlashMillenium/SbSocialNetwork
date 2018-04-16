package ru.sberbank.front.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditProfileController {
    @RequestMapping("users/edit")
    public String editProfile() {
        return "users/edit";
    }
}
