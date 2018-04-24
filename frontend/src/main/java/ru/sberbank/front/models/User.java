package ru.sberbank.front.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.gqw.dto.UserDTO;

import java.io.Serializable;

@ToString
public class User implements Serializable {
    UserDTO userDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

}
