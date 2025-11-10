package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Пользователь с id " + id + " не найден"));
    }

    @Override
    @Transactional
    public void createUser(User user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, Set<Role> roles) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() ->
                new NoSuchElementException("Пользователь с id " + user.getId() + " не найден"));

        existingUser.setUsername(user.getUsername());
        existingUser.setSurname(user.getSurname());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(roles);

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Пользователь с id " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Set<Role> getRolesByName(Set<String> roles) {
        return new HashSet<>(roleRepository.findByRoleIn(roles));
    }
}
