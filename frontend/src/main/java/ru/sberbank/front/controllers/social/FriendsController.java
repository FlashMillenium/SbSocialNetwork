package ru.sberbank.front.controllers.social;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FriendsController {
    @RequestMapping("social/friends")
    public String friends() {
        return "social/friends";
    }
}
