package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView profile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String login = (String) session.getAttribute("username");
        userDTO = userProfileService.profilePageGet(login);
        modelAndView.setViewName("users/profile");
        modelAndView.addObject("userDTO", userDTO);
        return modelAndView;
    }
}
