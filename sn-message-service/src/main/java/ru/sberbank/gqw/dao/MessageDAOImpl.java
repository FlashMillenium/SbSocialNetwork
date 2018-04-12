package ru.sberbank.gqw.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.sberbank.gqw.entity.Message;
import ru.sberbank.gqw.util.HibernateUtil;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MessageDAOImpl implements MessageDAO {
    //todo: надо ли закрывать сессии руками? найти нужную аннотацию
    public List<Message> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Message> messages = session.createQuery("from Message ").list();
        session.close();
        return messages;
    }

    @Transactional
    public void sendMessage(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.save(message);
        session.close();
    }
}
