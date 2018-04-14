package ru.sberbank.front.controllers.social;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
    @RequestMapping("socail/message")
    public String message() {
        return "social/message";
    }
}
