package ru.sberbank.gqw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.sberbank.gqw.dto.MessageDTO;

public interface MessageService {
    Page<MessageDTO> getAllMessages(Pageable pageable);

    Page<MessageDTO> getAllMessagesByUserId(Integer recipientId, Pageable pageable);

    Page<MessageDTO> getAllMessagesBetweenTwoUsers(Integer recipientId, Integer senderId, Pageable pageable);

    Page<MessageDTO> getAllUnreadMessagesByUserId(Integer recipientId, Pageable pageable);

    ResponseEntity<MessageDTO> sendMessage(MessageDTO newMessage);
}
