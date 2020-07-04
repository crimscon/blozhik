package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("{userId}/profile")
    public String userProfile(@PathVariable Long userId, Model model) {
        User user = userRepo.findById(userId).get();
        model.addAttribute("user", user);

        return "User/userProfile";
    }
}
