package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.UserEditService;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class EditProfileController {
    @Autowired
    UserEditService userEditService;
    UserDTO userDTO;

    @RequestMapping(value = "users/edit", method = RequestMethod.GET)
    public String editProfilePost() {
        return "users/edit";
    }

    @RequestMapping(value = "users/edit", method = RequestMethod.POST)
    public ModelAndView editProfilePost(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        userDTO = userEditService.updateValue(request, (String) session.getAttribute("username"));
        modelAndView.setViewName("redirect:profile");
        return modelAndView;
    }
}
