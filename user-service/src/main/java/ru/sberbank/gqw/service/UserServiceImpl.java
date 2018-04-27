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

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ResponseEntity<UserDTO> getByLogin(String login) {
        UserEntity user = userRepository.getOneByLogin(login);
        if (Objects.isNull(user)) {
            logger.warn("Trying get user wich don't contain in database, login {}", login);
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
        if (Objects.nonNull(newUser.getId())) {
            ResponseEntity<UserDTO> error = generateErrorResponse("When add new user you don't know id in my base. M'kay?");
            logger.warn("Input new User data with id in base before save in that base {}", newUser);
            return Optional.of(error);
        }
        if (userRepository.existsByLogin(newUser.getLogin())) {
            ResponseEntity<UserDTO> error = generateErrorResponse("User with this login already exist");
            logger.warn("Input new User data with login already in base {}", newUser);
            return Optional.of(error);
        }
        if (Objects.isNull(newUser.getPassword())) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Password must be set");
            logger.warn("Input new User data with password problem {}", newUser);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    @Transactional
    private ResponseEntity<UserDTO> addUserInBase(UserDTO newUser) {
        UserEntity toAddInBase = modelMapper.map(newUser, UserEntity.class);
        UserEntity savedInBase = userRepository.saveAndFlush(toAddInBase);
        UserDTO result = modelMapper.map(savedInBase, UserDTO.class);
        logger.debug("Successfully adding user in base");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(UserDTO updateUser) {
        Optional<ResponseEntity<?>> validate = validateUpdateUserData(updateUser);
        return validate.orElseGet(() -> updateUserData(updateUser));
    }

    private Optional<ResponseEntity<?>> validateUpdateUserData(UserDTO updateUser) {
        if (Objects.isNull(updateUser.getId())) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Updateble user don't have id");
            logger.warn("Input update User data with absent id {}", updateUser);
            return Optional.of(error);
        }
        UserEntity fromBD = userRepository.getOne(updateUser.getId());
        if (Objects.isNull(fromBD)) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Can't find user with this id in our base");
            logger.warn("User with that id don't find in base {}", updateUser);
            return Optional.of(error);
        }
        if (!fromBD.getLogin().equals(updateUser.getLogin())
                && userRepository.existsByLogin(updateUser.getLogin())) {
            HttpHeaders loginProblemHeader = new HttpHeaders();
            loginProblemHeader.add("UserMessage", "Problem with login updatable user.");
            loginProblemHeader.add("oldLogin", fromBD.getLogin());
            loginProblemHeader.add("newLogin", updateUser.getLogin());
            logger.warn("Problem with login updatable user {}", updateUser);
            return Optional.of(new ResponseEntity<>(loginProblemHeader, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(updateUser.getPassword())) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Updateble user don't have password");
            logger.warn("Input user data don't have password {}", updateUser);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    @Transactional
    private ResponseEntity<?> updateUserData(UserDTO updateUser) {
        UserEntity toUpdate = modelMapper.map(updateUser, UserEntity.class);
        UserEntity fromBD = userRepository.getOne(updateUser.getId());
        toUpdate.setFriends(fromBD.getFriends());
        userRepository.saveAndFlush(toUpdate);
        logger.debug("Successfully update user data in base");
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
        logger.debug("Successfully add Friend data in base");
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
        logger.debug("Successfully add Friend data in base");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Optional<ResponseEntity<?>> validateFriendData(Long id, Long friendId) {
        UserEntity friendHolder = userRepository.getOne(id);
        if (Objects.isNull(friendHolder)) {
            ResponseEntity<UserDTO> error = generateErrorResponse("User with that id don't exist");
            logger.warn("Id friend holder don't exist in base {}", id);
            return Optional.of(error);
        }
        UserEntity friend = userRepository.getOne(friendId);
        if (friendHolder.equals(friend)) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Can't be friend to yourself");
            logger.warn("Id friend holder ({}) and friend ({}) is same", id, friendId);
            return Optional.of(error);
        }
        if (Objects.isNull(friend)) {
            ResponseEntity<UserDTO> error = generateErrorResponse("Friend not found");
            logger.warn("Id friend don't exist in base {}", friendId);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    private <T> ResponseEntity<T> generateErrorResponse(String headerValue) {
        HttpHeaders header = new HttpHeaders();
        header.add("UserMessage", headerValue);
        return new ResponseEntity<>(header, HttpStatus.BAD_REQUEST);
    }

}
