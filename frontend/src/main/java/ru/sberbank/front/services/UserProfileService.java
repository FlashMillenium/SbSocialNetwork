package ru.sberbank.front.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.front.models.User;
import ru.sberbank.gqw.dto.UserDTO;

@Service
public class UserProfileService {
    @Autowired
    private UserLogin userLogin;
    private UserDTO userProfile;

    User user = new User();
    @GetMapping("users/profile")
    public UserDTO profilePageGet(String login) {
        userProfile = userLogin.getByLogin(login).getBody();
        user.setUserDTO(userProfile);
        return userProfile;
    }
}
