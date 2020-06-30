package com.project.MVC.controller;

import com.project.MVC.model.Role;
import com.project.MVC.model.User;
import com.project.MVC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepo;

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

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            model.addAttribute("user", user);
            model.addAttribute("password", password);
            model.addAttribute("password2", password2);
            model.addAttribute("email", email);
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
