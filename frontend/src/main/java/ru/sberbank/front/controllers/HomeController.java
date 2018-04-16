package ru.sberbank.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 09.04.2018
 */

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
