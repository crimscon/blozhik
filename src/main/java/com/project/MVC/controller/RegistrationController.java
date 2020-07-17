package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.enums.Role;
import com.project.MVC.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(defaultValue = "") String password,
                          @RequestParam(defaultValue = "") String password2,
                          @RequestParam(defaultValue = "") String email,
                          User user, Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("message", "Пароли не одинаковые!");
            model.addAttribute("username", user != null ? user.getUsername() : "");
            model.addAttribute("password", password);
            model.addAttribute("password2", password2);
            model.addAttribute("email", email);
            return "registration";
        }

        User userFromDb = (User) userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            model.addAttribute("user", user);
            model.addAttribute("password", password);
            model.addAttribute("password2", password2);
            model.addAttribute("email", email);
            return "registration";
        }

        user.setActive(true);

        Set<Role> roleSet = new HashSet<>();

        if (userService.findAll().isEmpty()) {
            roleSet.add(Role.ADMIN);
        }

        roleSet.add(Role.USER);
        user.setRoles(roleSet);

        userService.save(user);

        return "redirect:/login";
    }

}
