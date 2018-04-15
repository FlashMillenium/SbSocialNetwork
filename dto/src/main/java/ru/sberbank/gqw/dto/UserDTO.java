package ru.sberbank.gqw.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDateTime dateOfBirth;

    private String about;
}
