package ru.sberbank.front.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.gqw.dto.UserDTO;

@Service
public class UserProfileService {
    @Autowired
    UserLogin userLogin;
    public UserDTO getUserProfile() {
        return userProfile;
    }
    UserDTO userProfile;
    @GetMapping("users/profile")
    public UserDTO profilePageGet(String login) {
        userProfile = userLogin.getByLogin(login).getBody();
        return userProfile;
    }
}
