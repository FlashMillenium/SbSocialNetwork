package ru.sberbank.gqw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.sberbank.gqw.dto.UserDTO;
import ru.sberbank.gqw.model.UserEntity;

public interface UserService {

    public ResponseEntity<UserDTO> getByLogin(String login);


    public Page<UserDTO>  getFriends(Long id, Pageable pageable);


    public ResponseEntity<?> addUser(UserDTO newUser);


    public ResponseEntity<?> updateUser( UserDTO updateUser);


    public ResponseEntity<?> deleteFriend( Long id,  UserDTO friendToDelete);

    public ResponseEntity<?> addFriend( Long id, UserDTO friendToAdd);

}
