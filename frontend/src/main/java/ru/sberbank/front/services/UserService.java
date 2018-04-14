package ru.sberbank.front.services;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 10.04.2018
 */


public interface UserService {
    boolean authenticate(String username, String password);
}
