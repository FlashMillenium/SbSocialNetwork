package ru.sberbank.front.controllers.social;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhotoController {
    @RequestMapping("social/photo")
    public String photo() {
        return "social/photo";
    }
}
