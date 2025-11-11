package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface AdminService {
    List<User> findAll();

    User findById(Long id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}
