package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.addUser.AddUser;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpSession;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 26.04.2018
 */

@Controller
public class AddUserController {
    @Autowired
    AddUser addUser;

    @RequestMapping(value = "/users/add/", method = RequestMethod.POST)
    public ModelAndView addFriends(HttpSession session, @RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResponseEntity<UserDTO> currentUser = addUser.getByLogin((String) session.getAttribute("username"));
        addUser.addFriend(currentUser.getBody().getId(), id);
        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }
}
