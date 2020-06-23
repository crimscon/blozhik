package com.project.MVC.controller;

import com.project.MVC.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private UserRepository userRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        if (userRepository == null || userRepository.findAll().isEmpty()) return "redirect:registration";

        model.addAttribute("title", "main");
        return "main";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        if (userRepository == null || userRepository.findAll().isEmpty()) {
            model.addAttribute("title", "registration");
            return "registration";
        } else return "redirect:main";
    }
}
