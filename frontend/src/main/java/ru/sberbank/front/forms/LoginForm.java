package ru.sberbank.front.forms;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.gqw.dto.UserDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
    @Size(min=5, max=30, message = "Username size should be in the range [5...30]")
    private String username;

    @NotNull
    @Size(min=5, max=50, message = "Password size should be in the range [5...50]")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}