package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.allUser.ListUserService;

import javax.servlet.http.HttpSession;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 25.04.2018
 */

@Controller
public class AllUserController {
    @Autowired
    ListUserService listUserService;
    Page page;

    @RequestMapping("users/all")
    public ModelAndView getAllUsers(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/all");
        page = listUserService.getOtherUsers((String) session.getAttribute("username"));
        return modelAndView;
    }
}
