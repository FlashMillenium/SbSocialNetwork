package ru.sberbank.gqw.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
    //todo: add field 'read'
    private int senderId;
    private int recipientId;
    private String message;
    private LocalDateTime sentAt;

    public Message(int senderId, int recipientId, String message) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
        this.sentAt = LocalDateTime.now();
    }

    public Message(int senderId, int recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.sentAt = LocalDateTime.now();
    }

    public Message() {
    }

    @Id
    @Column(name = "sender_id")
    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    @Id
    @Column(name = "recipient_id")
    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    @Column
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Id
    @Column(name = "sent_at")
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return senderId == message1.senderId &&
                recipientId == message1.recipientId &&
                Objects.equals(message, message1.message) &&
                Objects.equals(sentAt, message1.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, recipientId, message, sentAt);
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}