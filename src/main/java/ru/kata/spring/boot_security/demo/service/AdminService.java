package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface AdminService {
    List<User> findAll();

    User findById(Long id);

    void createUser(User user, Set<Role> roles);

    void updateUser(User user, Set<Role> roles);

    void deleteUser(Long id);

    Set<Role> getRolesByName(Set<String> roles);
}
