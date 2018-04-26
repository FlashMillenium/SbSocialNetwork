package ru.sberbank.front.controllers.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.RestResponsePage;
import ru.sberbank.front.services.friends.FriendsList;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpSession;

@Controller
public class FriendsController {
    @Autowired
    FriendsList friendsList;

    @RequestMapping("social/friends")
    public ModelAndView getFriends(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("social/friends");
        long currentUserID = friendsList.getByLogin((String) session.getAttribute("username")).getBody().getId();
        RestResponsePage<UserDTO> page = friendsList.getFriends(currentUserID, new PageRequest(0, 10));
        modelAndView.addObject("users", page.getContent());
        return modelAndView;
    }
}
