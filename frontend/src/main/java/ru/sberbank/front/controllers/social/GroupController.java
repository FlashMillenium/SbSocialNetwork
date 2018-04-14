package ru.sberbank.front.controllers.social;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupController {
    @RequestMapping("socail/controller")
    public String group() {
        return "socail/controller";
    }
}
