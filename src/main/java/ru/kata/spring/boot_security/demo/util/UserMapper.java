package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        userDto.setRoles(user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet()));

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        Set<Role> roles = userDto.getRoles().stream()
                .map(role -> roleRepository.findByRole(role).orElseThrow(() ->
                        new NoSuchElementException("Роль не найдена: " + role)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        return user;
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
