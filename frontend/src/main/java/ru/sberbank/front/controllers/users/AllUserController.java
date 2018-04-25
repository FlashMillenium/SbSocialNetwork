package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.allUser.ListUser;
import ru.sberbank.front.services.allUser.ListUserService;
import ru.sberbank.front.services.allUser.RestResponsePage;
import ru.sberbank.gqw.dto.UserDTO;

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
    Page<UserDTO> page;

    @Autowired
    ListUser listUser;

    @RequestMapping("users/all")
    public ModelAndView getAllUsers(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/all");
        RestResponsePage<UserDTO> page = listUser.getOtherUsers((String) session.getAttribute("username"), new PageRequest(0, 10));
        System.out.println(page.getContent());
        return modelAndView;
    }
}
