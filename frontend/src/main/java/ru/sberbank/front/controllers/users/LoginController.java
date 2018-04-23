package ru.sberbank.front.controllers.users;

import org.springframework.http.ResponseEntity;
import ru.sberbank.front.forms.LoginForm;
import ru.sberbank.front.models.User;
import ru.sberbank.front.services.NotificationService;
//import ru.sberbank.front.services.UserService_RM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.sberbank.front.services.UsersLoginTest;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

//    @Autowired
//    private UserService_RM userService;
//
//    @Autowired
//    private NotificationService notifyService;
//
//    @ModelAttribute("user")
//    public User setUpUserForm() {
//        return new User();
//    }
//    @RequestMapping("/users/login")
//    public String login(LoginForm loginForm) {
//        return "users/login";
//    }
//
//
//    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
//    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult, @SessionAttribute("user") User user) {
//        if (bindingResult.hasErrors()) {
//            notifyService.addErrorMessage("Please fill the form correctly!");
//            return "users/login";
//        }
//
//        if (!userService.authenticate(loginForm.getUsername(), loginForm.getPassword())) {
//            notifyService.addErrorMessage("Invalid login!");
//            return "users/login";
//        } else {
//            notifyService.addInfoMessage("Login successful");
//            user.setUsername(loginForm.getUsername());
//            System.out.println(user.getUsername());
//
//            return "redirect:profile";
//        }


//    }
@Autowired
UsersLoginTest usersLoginTest;

    @RequestMapping(value = "users/login", method = RequestMethod.GET)
    public String loginPageGet() {
        return "users/login";
    }

    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginPagePost(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println("LoginContr " + login);
        System.out.println("LoginContr " + password);
        ResponseEntity<UserDTO> loginFromMicroserv = usersLoginTest.getLoginFromMicroserv(login);
        if (loginFromMicroserv.getBody().getPassword().equals(password)) {
            return "redirect:profile";
        } else {
            return "redirect:login";
        }
    }


}
