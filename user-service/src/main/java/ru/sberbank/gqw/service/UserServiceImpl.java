package ru.sberbank.gqw.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.UserDTO;
import ru.sberbank.gqw.model.UserEntity;
import ru.sberbank.gqw.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ResponseEntity<UserDTO> getByLogin(String login) {
        UserEntity user = userRepository.getOneByLogin(login);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(userRepository.getOneByLogin(login), UserDTO.class), HttpStatus.OK);
    }

    @Override
    public Page<UserDTO> getFriends(Long id, Pageable pageable) {
        Page<UserEntity> friendsById = userRepository.getAllFriendsById(id, pageable);
        return friendsById.map(e -> modelMapper.map(e, UserDTO.class));
    }

   @Override
    public Page<UserDTO> getOtherUsers(String login, Pageable pageable) {
        Page<UserEntity> allUsers = userRepository.findAllByLoginNot(login, pageable);
        return allUsers.map(e -> modelMapper.map(e, UserDTO.class));
    }

    @Override
    public ResponseEntity<UserDTO> addUser(UserDTO newUser) {
        Optional<ResponseEntity<UserDTO>> validateEntity = validateNewUserData(newUser);
        return validateEntity.orElseGet(() -> addUserInBase(newUser));

    }

    private Optional<ResponseEntity<UserDTO>> validateNewUserData(UserDTO newUser) {
        String headerName = "UserMessage";
        if (Objects.nonNull(newUser.getId())) {
            HttpHeaders idMustBeNullHeader = new HttpHeaders();
            idMustBeNullHeader.add(headerName, "When add new user you don't know id in my base. M'kay?");
            return Optional.of(new ResponseEntity<>(idMustBeNullHeader, HttpStatus.BAD_REQUEST));
        }
        if (userRepository.existsByLogin(newUser.getLogin())) {
            HttpHeaders userAlreadyExistHeader = new HttpHeaders();
            userAlreadyExistHeader.add(headerName, "User with this login already exist");
            return Optional.of(new ResponseEntity<>(userAlreadyExistHeader, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(newUser.getPassword())) {
            HttpHeaders passAlreadyExistHeader = new HttpHeaders();
            passAlreadyExistHeader.add(headerName, "Password must be set");
            return Optional.of(new ResponseEntity<>(passAlreadyExistHeader, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }

    @Transactional
    private ResponseEntity<UserDTO> addUserInBase(UserDTO newUser) {
        UserEntity toAddInBase = modelMapper.map(newUser, UserEntity.class);
        UserEntity savedInBase = userRepository.saveAndFlush(toAddInBase);
        UserDTO result = modelMapper.map(savedInBase, UserDTO.class);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUser(UserDTO updateUser) {
        Optional<ResponseEntity<?>> validate = validateUpdateUserData(updateUser);
        return validate.orElseGet(() -> updateUserData(updateUser));
    }

    private Optional<ResponseEntity<?>> validateUpdateUserData(UserDTO updateUser) {
        String messageHeader = "UserMessage";
        if (Objects.isNull(updateUser.getId())) {
            HttpHeaders idIsNullHeader = new HttpHeaders();
            idIsNullHeader.add(messageHeader, "Updateble user don't have id");
            return Optional.of(new ResponseEntity<>(idIsNullHeader, HttpStatus.BAD_REQUEST));
        }
        UserEntity fromBD = userRepository.getOne(updateUser.getId());
        if (Objects.isNull(fromBD)) {
            HttpHeaders userNotFindHeader = new HttpHeaders();
            userNotFindHeader.add(messageHeader, "Can't find user with this id in our base");
            return Optional.of(new ResponseEntity<>(userNotFindHeader, HttpStatus.BAD_REQUEST));
        }
        if (!fromBD.getLogin().equals(updateUser.getLogin())
                && userRepository.existsByLogin(updateUser.getLogin())) {
            HttpHeaders loginProblemHeader = new HttpHeaders();
            loginProblemHeader.add(messageHeader, "Problem with login updatable user.");
            loginProblemHeader.add("oldLogin", fromBD.getLogin());
            loginProblemHeader.add("newLogin", updateUser.getLogin());
            return Optional.of(new ResponseEntity<>(loginProblemHeader, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(updateUser.getPassword())) {
            HttpHeaders passProblemHeader = new HttpHeaders();
            passProblemHeader.add(messageHeader, "Updateble user don't have password");
            return Optional.of(new ResponseEntity<>(passProblemHeader, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }

    @Transactional
    private ResponseEntity<?> updateUserData(UserDTO updateUser) {
        UserEntity toUpdate = modelMapper.map(updateUser, UserEntity.class);
        UserEntity fromBD = userRepository.getOne(updateUser.getId());
        toUpdate.setFriends(fromBD.getFriends());
        userRepository.saveAndFlush(toUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addFriend(Long id, Long friendId) {
        Optional<ResponseEntity<?>> validateFriend = validateFriendData(id, friendId);
        return validateFriend.orElseGet(() -> addFriendToBase(id, friendId));

    }

    @Transactional
    private ResponseEntity<?> addFriendToBase(Long id, Long friendId) {
        UserEntity friendHolder = userRepository.getOne(id);
        UserEntity friend = userRepository.getOne(friendId);
        friendHolder.getFriends().add(friend);
        userRepository.saveAndFlush(friendHolder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteFriend(Long id, Long friendId) {
        Optional<ResponseEntity<?>> validateFriend = validateFriendData(id, friendId);
        return validateFriend.orElseGet(() -> deleteFriendFromBase(id, friendId));

    }

    @Transactional
    private ResponseEntity<?> deleteFriendFromBase(Long id, Long friendId) {
        UserEntity friendHolder = userRepository.getOne(id);
        UserEntity friend = userRepository.getOne(friendId);
        friendHolder.getFriends().remove(friend);
        userRepository.saveAndFlush(friendHolder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Optional<ResponseEntity<?>> validateFriendData(Long id, Long friendId) {
        UserEntity friendHolder = userRepository.getOne(id);
        if (Objects.isNull(friendHolder)) {
            HttpHeaders header = new HttpHeaders();
            header.add("UserMessage", "User with that id don't exist");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        UserEntity friend = userRepository.getOne(friendId);
        if (friendHolder.equals(friend)) {
            HttpHeaders header = new HttpHeaders();
            header.add("UserMessage", "Can't be friend to yourself");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(friend)) {
            HttpHeaders header = new HttpHeaders();
            header.add("UserMessage", "Friend not found");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }

}
