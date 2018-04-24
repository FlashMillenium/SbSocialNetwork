package ru.sberbank.front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserEditService {
    @Autowired
    private UserEdit userEdit;
    UserDTO userDTO;

    public UserDTO updateValue(HttpServletRequest request, String username) {
        userDTO = getByLogin(username).getBody();
        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("secondName"));
        userDTO.setDateOfBirth(convert(request.getParameter("dateOfBirth")));
        return updateUser(userDTO).getBody();
    }

    @PutMapping("/users/update")
    public ResponseEntity<UserDTO> updateUser(UserDTO updateUser) {
        return userEdit.updateUser(updateUser);
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDTO> getByLogin(String login) {
        return userEdit.getByLogin(login);
    }

    private LocalDateTime convert(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(dateOfBirth, formatter);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.MIN.toLocalTime());
        return ldt;
    }
}
