package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping
    public String showAdminPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", adminService.findAll());
        return "pages/admin-page";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        adminService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        adminService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        adminService.deleteUser(id);
        return "redirect:/admin";
    }
}
