package ru.sberbank.gqw.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {
    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDateTime dateOfBirth;

    private String about;
}
