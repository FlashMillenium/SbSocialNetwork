package ru.sberbank.gqw.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.MessageDTO;
import ru.sberbank.gqw.model.Message;
import ru.sberbank.gqw.repository.MessageRepository;

@Service("msgService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        Page<Message> messages = messageRepository.findAll(pageable);
        return messages.map(e -> modelMapper.map(e, MessageDTO.class));
    }

    @Override
    public Page<MessageDTO> getAllMessagesByUserId(Integer recipientId, Pageable pageable) {
        Page<Message> messages = messageRepository
                .findAllByRecipientId(recipientId, pageable);
        return messages.map(e -> modelMapper.map(e, MessageDTO.class));
    }

    @Override
    public Page<MessageDTO> getAllMessagesBetweenTwoUsers(Integer recipientId, Integer senderId, Pageable pageable) {
        Page<Message> messages = messageRepository
                .findAllBySenderIdAndRecipientId(senderId, recipientId, pageable);
        return messages.map(e -> modelMapper.map(e, MessageDTO.class));
    }

    @Override
    public Page<MessageDTO> getAllUnreadMessagesByUserId(Integer recipientId, Pageable pageable) {
        Page<Message> messages = messageRepository
                .findAllByRecipientIdAndSeen(recipientId, false, pageable);
        return messages.map(e -> modelMapper.map(e, MessageDTO.class));
    }

    @Override
    public ResponseEntity<MessageDTO> sendMessage(MessageDTO newMessage) {
        Message toAddInDB = modelMapper.map(newMessage, Message.class);
        Message savedInBase = messageRepository.saveAndFlush(toAddInDB);
        MessageDTO result = modelMapper.map(savedInBase, MessageDTO.class);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
