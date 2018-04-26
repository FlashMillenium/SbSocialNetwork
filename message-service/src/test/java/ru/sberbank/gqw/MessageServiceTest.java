package ru.sberbank.gqw;


import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sberbank.gqw.dto.MessageDTO;
import ru.sberbank.gqw.model.Message;
import ru.sberbank.gqw.repository.MessageRepository;
import ru.sberbank.gqw.service.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    private List<Message> messages = new ArrayList<>();

    @Test
    public void contextLoad() {
    }

    @Before
    public void setUp() {
        messages.add(new Message(1, 2, "first", false));
        messages.add(new Message(1, 2, "first", true));
        messages.add(new Message(1, 2, "first", false));
        messages.add(new Message(3, 2, "first", false));
        messages.add(new Message(2, 3, "first", false));
        messages.add(new Message(2, 1, "first", false));

        messageRepository.save(messages);
        messageRepository.flush();
    }

    @After
    public void drop() {
        messageRepository.deleteAll();
    }

    @Test()
    public void getAllMessagesTest() {
        assertEquals(messages.size(), messageService.getAllMessages(new PageRequest(1, 1)).getTotalPages());
    }

    @Test()
    public void sendMessageTest() {
        MessageDTO messageDTO = new MessageDTO(messages.size() + 1, 1, 2, "new", LocalDateTime.now(), false);
        assertEquals(HttpStatus.CREATED, messageService.sendMessage(messageDTO).getStatusCode());
    }

    @Test()
    public void getAllMessagesForUserTest() {

        for (Message m : messages) {
            long res = messages.stream()
                    .filter(x -> m.getRecipientId() == x.getRecipientId())
                    .count();

            assertEquals(res, messageService.getAllMessagesByUserId(m.getRecipientId(), new PageRequest(1, 1)).getTotalPages());
        }
    }

    @Test()
    public void getAllMessagesBetweenTwoUsersTest() {
        for (Message m : messages) {
            long res = messages.stream()
                    .filter(x -> m.getRecipientId() == x.getRecipientId())
                    .filter(x -> m.getSenderId() == x.getSenderId())
                    .count();

            assertEquals(res, messageService.getAllMessagesBetweenTwoUsers(m.getRecipientId(),
                    m.getSenderId(), new PageRequest(1, 1)).getTotalPages());
        }
    }

    @Test()
    public void getAllUnreadMessagesForUserTest() {
        for (Message m : messages) {
            long res = messages.stream()
                    .filter(x -> m.getRecipientId() == x.getRecipientId())
                    .filter(x -> !x.isSeen())
                    .count();

            assertEquals(res, messageService.getAllUnreadMessagesByUserId(m.getRecipientId(),
                    new PageRequest(1, 1)).getTotalPages());
        }

    }
}
