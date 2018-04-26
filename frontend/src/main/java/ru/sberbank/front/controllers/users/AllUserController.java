package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.allUser.ListUser;
import ru.sberbank.front.services.RestResponsePage;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpSession;



@Controller
public class AllUserController {
    @Autowired
    ListUser listUser;

    @RequestMapping("users/all")
    public ModelAndView getAllUsers(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/all");
        RestResponsePage<UserDTO> page = listUser.getOtherUsers((String) session.getAttribute("username"), new PageRequest(0, 10));
        modelAndView.addObject("users", page.getContent());
        return modelAndView;
    }
}
