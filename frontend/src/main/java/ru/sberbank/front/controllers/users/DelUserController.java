package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.delUser.DelUser;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpSession;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 26.04.2018
 */

@Controller
public class DelUserController {
    @Autowired
    DelUser delUser;

    @RequestMapping(value = "/users/del/", method = RequestMethod.POST)
    public ModelAndView addFriends(HttpSession session, @RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResponseEntity<UserDTO> currentUser = delUser.getByLogin((String) session.getAttribute("username"));
        delUser.deleteFriend(currentUser.getBody().getId(), id);
        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }
}
