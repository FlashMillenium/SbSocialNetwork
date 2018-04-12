package ru.sberbank.gqw.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.sberbank.gqw.entity.Message;
import ru.sberbank.gqw.util.HibernateUtil;


import java.util.List;

@Component
public class MessageDAOImpl implements MessageDAO {

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
        System.out.println(message.toString());
        session.close();
    }
}
