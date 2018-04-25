package ru.sberbank.gqw.services;

import ru.sberbank.gqw.entity.Message;

import java.util.List;

public interface MessageService {
    List getAll();
    void sendMessage(Message message);
    List getAllMessagesForUser(int userId);
    List getUnreadMessagesForUser(int userId);
    List getAllMessagesFromUserToUser(int senderId, int recipientId);

}
