package ru.sberbank.gqw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.gqw.model.Message;

@Repository("msgRepository")
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Override
    Page<Message> findAll(Pageable pageable);

    Page<Message> findAllByRecipientId(Integer recipientId, Pageable pageable);

    Page<Message> findAllBySenderIdAndRecipientId(Integer senderId, Integer recipientId, Pageable pageable);

    Page<Message> findAllByRecipientIdAndSeen(Integer recipientId, Boolean isSeen, Pageable pageable);
}
