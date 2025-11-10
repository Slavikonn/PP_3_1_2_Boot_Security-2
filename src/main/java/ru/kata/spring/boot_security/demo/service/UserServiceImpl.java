package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с именем '" + username + "' не найден"));
    }
}
