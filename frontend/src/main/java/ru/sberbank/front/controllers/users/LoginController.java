package ru.sberbank.front.controllers.users;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.UsersLoginService;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {




@Autowired
UsersLoginService usersLoginService;

    @RequestMapping(value = "users/login", method = RequestMethod.GET)
    public ModelAndView loginPageGet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/login");
        return modelAndView;
    }


    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public ModelAndView loginPagePost(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ResponseEntity<UserDTO> loginFromMicroserv = usersLoginService.getLoginFromMicroserv(login);
        if (loginFromMicroserv.getBody().getPassword().equals(password)) {
            session.setAttribute("username", login);
            modelAndView.addObject("userDTO", loginFromMicroserv.getBody());
            modelAndView.setViewName("redirect:profile");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }
    }

}
