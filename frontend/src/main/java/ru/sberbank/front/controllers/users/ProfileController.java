package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.front.services.UserProfileService;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpSession;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 11.04.2018
 */

@Controller
public class ProfileController {
    @Autowired
    UserProfileService userProfileService;
    UserDTO userDTO = new UserDTO();

    @RequestMapping(value = "/users/profile")
    public String profile(HttpSession session) {
        String login = (String) session.getAttribute("username");
        System.out.println(login);
        userDTO = userProfileService.profilePageGet(login);
        return "users/profile";
    }
}
