package ru.sberbank.gqw.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sberbank.gqw.dto.UserDTO;
import ru.sberbank.gqw.model.UserEntity;
import ru.sberbank.gqw.repository.UserRepository;
import ru.sberbank.gqw.service.UserService;
import ru.sberbank.gqw.service.UserServiceImpl;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Before
    public void setUp() throws Exception {
        UserEntity first = new UserEntity("father", "fgfgfgfg");
        UserEntity second = new UserEntity("son", "fgfgfgfg");
        UserEntity third = new UserEntity("holySpirit", "fgfgfgfg");
        UserEntity four = new UserEntity("friendForAll", "fgfgfgfg");
        Set<UserEntity> friends = four.getFriends();
        friends.add(first);
        friends.add(second);
        friends.add(third);
        userRepository.save(first);
        userRepository.save(second);
        userRepository.save(third);
        userRepository.save(four);
        userRepository.flush();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void checkUserReturnByLogin() {
        UserDTO expected = UserDTO.builder().login("father").password("fgfgfgfg").build();
        ResponseEntity<UserDTO> actual = userService.getByLogin("father");
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected.getLogin(), actual.getBody().getLogin());
        assertNotNull(actual.getBody().getId());
    }

    @Test
    @Transactional
    public void checkGetFriend() {
        UserEntity friendForAll = userRepository.getOneByLogin("friendForAll");
        List<String> actualList = friendForAll.getFriends().stream().map(UserEntity::getLogin).collect(Collectors.toList());
        Page<UserDTO> firstExpectedPage = userService.getFriends(friendForAll.getId(), new PageRequest(0, 2));
        firstExpectedPage.getContent().stream().map(UserDTO::getLogin).forEach(e -> assertTrue(actualList.contains(e)));
        assertTrue(firstExpectedPage.hasNext());
        Page<UserDTO> secondExpectedPage = userService.getFriends(friendForAll.getId(), firstExpectedPage.nextPageable());
        secondExpectedPage.getContent().stream().map(UserDTO::getLogin).forEach(e -> assertTrue(actualList.contains(e)));
        assertFalse(secondExpectedPage.hasNext());
        assertEquals(3L, secondExpectedPage.getTotalElements());
    }

    @Test
    public void checkUserBeenAdded() {
        UserDTO expected = UserDTO.builder().login("TestUser").password("TestPassword").build();
        userService.addUser(expected);
        UserEntity actual = userRepository.getOneByLogin(expected.getLogin());
        assertNotNull(actual);
        assertEquals(expected.getLogin(), actual.getLogin());
    }

    @Test
    @Transactional
    public void checkUserBeenUpdated() {
        UserDTO toUpdate = userService.getByLogin("son").getBody();
        toUpdate.setAbout("I'm JESUS!");
        toUpdate.setDateOfBirth(LocalDateTime.of(0, 1, 1, 0, 0));
        userService.updateUser(toUpdate);
        UserEntity actual = userRepository.getOneByLogin("son");
        assertNotNull(actual.getDateOfBirth());
        assertEquals("I'm JESUS!", actual.getAbout());
        assertEquals(toUpdate.getId(), actual.getId());
    }

    @Test
    @Transactional
    public void checkFriendBeenAdded() {
        UserDTO son = userService.getByLogin("son").getBody();
        UserDTO futureFriend = userService.getByLogin("holySpirit").getBody();
        userService.addFriend(son.getId(), futureFriend.getId());
        Set<UserEntity> actualFriends = userRepository.getOneByLogin("son").getFriends();
        assertEquals(1, actualFriends.size());
        UserEntity actualFriend = actualFriends.stream().findFirst().get();
        assertEquals(futureFriend.getLogin(), actualFriend.getLogin());
    }

    @Test
    @Transactional
    public void checkFriendBeenDeleted() {
        UserDTO friendForAll = userService.getByLogin("friendForAll").getBody();
        UserDTO toDelete = userService.getByLogin("father").getBody();
        ResponseEntity<?> responseEntity = userService.deleteFriend(friendForAll.getId(), toDelete.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<?> mustBeIdempotent = userService.deleteFriend(friendForAll.getId(), toDelete.getId());
        assertEquals(HttpStatus.OK, mustBeIdempotent.getStatusCode());
        Set<UserEntity> actualFriends = userRepository.getOneByLogin("friendForAll").getFriends();
        assertEquals(2, actualFriends.size());
        boolean mustAbsent = actualFriends.stream().map(UserEntity::getLogin).anyMatch("father"::equals);
        assertFalse(mustAbsent);
    }


}