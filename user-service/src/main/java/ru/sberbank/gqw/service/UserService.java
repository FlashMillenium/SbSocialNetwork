package ru.sberbank.gqw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.sberbank.gqw.dto.UserDTO;

public interface UserService {

    public ResponseEntity<UserDTO> getByLogin(String login);


    public Page<UserDTO> getFriends(Long id, Pageable pageable);

    public Page<UserDTO> getOtherUsers(String login, Pageable pageable);

    public ResponseEntity<UserDTO> addUser(UserDTO newUser);


    public ResponseEntity<?> updateUser(UserDTO updateUser);


    public ResponseEntity<?> deleteFriend(Long id, Long friendId);

    public ResponseEntity<?> addFriend(Long id, Long friendId);

}
