package ru.sberbank.gqw.services;

import ru.sberbank.gqw.entity.Message;

import java.util.List;

public interface MessageService {
    public List getAll();
    public void sendMessage(Message message);
}
