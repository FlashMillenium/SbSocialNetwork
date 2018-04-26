package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.registr.UserRegistrationService;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;


@Controller
public class RegistrationController {
    @Autowired
    UserRegistrationService userRegistration;

    @RequestMapping(value = "users/registration", method = RequestMethod.GET)
    public String registrPageGet() {
//        ResponseEntity<UserDTO> registrFromMicroserv = userRegistration.getRegistrFromMicroserv("aaa", "bbb");
//        System.out.println(registrFromMicroserv.getStatusCode().getReasonPhrase());
//        System.out.println("!!!!!");
//        System.out.println(registrFromMicroserv.getBody().getId());
        return "users/registration";
    }
    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public ModelAndView registrPagePost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:login");
//        Add processing
        ResponseEntity<UserDTO> registrFromMicroserv = userRegistration.getRegistrFromMicroserv(request.getParameter("password"), request.getParameter("login"));
        return modelAndView;
    }
}
