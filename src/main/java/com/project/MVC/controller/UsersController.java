package com.project.MVC.controller;

import com.project.MVC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    //отложено

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        if (userRepository == null || userRepository.findAll().isEmpty()) {
            model.addAttribute("title", "registration");
            return "registration";
        } else return "redirect:main";
    }
}
