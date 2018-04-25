package ru.sberbank.gqw.services;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.sberbank.gqw.entity.Message;
import ru.sberbank.gqw.util.HibernateUtil;


import javax.persistence.Query;
import java.util.List;

//todo: не закрывать сессии
@Component
public class MessageServiceImpl implements MessageService {

    public List<Message> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Message> messages = session.createQuery("from Message ").list();
        session.close();
        return messages;
    }

    public void sendMessage(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(message);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List getAllMessagesForUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Message as M where M.recipientId = :userId");
        query.setParameter("userId", userId);
        List<Message> messages = query.getResultList();
        session.close();
        return messages;
    }

    @Override
    public List getUnreadMessagesForUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Message as M where M.recipientId = :userId and M.seen = false");
        query.setParameter("userId", userId);
        List<Message> messages = query.getResultList();
        session.close();
        return messages;
    }

    @Override
    public List getAllMessagesFromUserToUser(int senderId, int recipientId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Message as M where M.recipientId = :recipientId and M.senderId = :senderId");
        query.setParameter("senderId", senderId);
        query.setParameter("recipientId", recipientId);
        List<Message> messages = query.getResultList();
        session.close();
        return messages;
    }
}
