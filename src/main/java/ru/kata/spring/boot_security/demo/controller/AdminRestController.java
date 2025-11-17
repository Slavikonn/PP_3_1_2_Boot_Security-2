package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.util.UserMapper;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
public class AdminRestController {
    private final AdminService adminService;
    private final UserMapper userMapper;

    public AdminRestController(AdminService adminService, UserMapper userMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = adminService.findAll();
        return ResponseEntity.ok(userMapper.toDtoList(users));
    }
}
