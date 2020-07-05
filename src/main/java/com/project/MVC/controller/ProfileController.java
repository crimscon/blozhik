package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessagesService messagesService;

    @GetMapping("{username}/profile")
    public String userProfile(@PathVariable String username, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(username);

        if (!user.getId().equals(currentUser.getId()))
            model.addAttribute("messages", messagesService.findByAuthor(user.getId()));

        model.addAttribute("userProfile", user);

        return "User/userProfile";
    }

    @GetMapping("{username}/profile/messages")
    public String userMessages(@PathVariable String username, Model model) {
        User user = (User) userService.loadUserByUsername(username);

        model.addAttribute("messages", messagesService.findByAuthor(user.getId()));

        return userProfile(username, model);
    }

    @GetMapping("{username}/profile/edit")
    public String userEdit(@PathVariable String username, Model model) {
        return "redirect:/{username}/profile";
    }

    @PostMapping("{username}/profile")
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("userId") Long userId
    ) {
        User user = userService.findById(userId);
        user.setUsername(username);
        user.setPassword(password);

        userService.save(user);

        return "redirect:/{username}/profile";
    }


}
