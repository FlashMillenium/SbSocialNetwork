package ru.sberbank.front.services;

import org.springframework.stereotype.Service;

import java.io.ObjectOutput;
import java.util.Objects;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 10.04.2018
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean authenticate(String username, String password) {
        return Objects.equals(username, password);
    }
}
