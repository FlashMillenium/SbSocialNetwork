package ru.sberbank.front.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import ru.sberbank.front.services.login.UserLogin;
import ru.sberbank.gqw.dto.UserDTO;

@Service
public class UserProfileService {
    @Autowired
    private UserLogin userLogin;
    private UserDTO userProfile;

    @GetMapping("users/profile")
    public UserDTO profilePageGet(String login) {
        userProfile = userLogin.getByLogin(login).getBody();
        return userProfile;
    }
}
