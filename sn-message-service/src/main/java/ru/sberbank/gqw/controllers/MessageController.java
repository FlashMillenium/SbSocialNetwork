package ru.sberbank.gqw.controllers;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.sberbank.gqw.entity.Message;
import ru.sberbank.gqw.util.HibernateUtil;

import java.util.List;

@Component
public class MessageController {
    public List<Message> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Message> messages = session.createQuery("from Message ").list();
//        HibernateUtil.shutdown();
        return messages;
    }
}
