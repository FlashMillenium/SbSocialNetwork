package ru.sberbank.front.services;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 09.04.2018
 */

public interface NotificationService {
    void addInfoMessage(String msg);
    void addErrorMessage(String msg);
}
