package ru.sberbank.gqw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.gqw.dto.MessageDTO;
import ru.sberbank.gqw.service.MessageService;

@RestController("msgController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages/all")
    public Page<MessageDTO> showAll(@PageableDefault Pageable pageable) {
        System.out.println(messageService.getAllMessages(pageable).toString());
        return messageService.getAllMessages(pageable);
    }

    @GetMapping("/messages/{userId}")
    public Page<MessageDTO> showMessagesByUserId(@PathVariable(value = "userId") Integer userId,
                                                 @PageableDefault Pageable pageable) {
        return messageService.getAllMessagesByUserId(userId, pageable);
    }

    @GetMapping("/messages/{userId}/from/{senderId}")
    public Page<MessageDTO> showMessagesBetweenTwoUsers(@PathVariable(value = "userId") Integer userId,
                                                        @PathVariable(value = "senderId") Integer senderId,
                                                        @PageableDefault Pageable pageable) {
        return messageService.getAllMessagesBetweenTwoUsers(userId, senderId, pageable);
    }

    @GetMapping("/messages/{userId}/unread")
    public Page<MessageDTO> showUnreadMessagesByUserId(@PathVariable(value = "userId") Integer userId,
                                                       @PageableDefault Pageable pageable) {
        return messageService.getAllUnreadMessagesByUserId(userId, pageable);
    }

    @PostMapping("/message/sending")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO newMessage) {
        return messageService.sendMessage(newMessage);
    }

}
