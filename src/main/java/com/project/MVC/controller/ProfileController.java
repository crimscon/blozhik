package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.enums.Color;
import com.project.MVC.model.enums.Sex;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessagesService messagesService;

    @GetMapping("{username}/profile")
    public String userProfile(@PathVariable String username,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(username);

        if (!user.getId().equals(currentUser.getId())) {
            model.addAttribute("page", messagesService.findByAuthor(user.getId(), pageable));
            model.addAttribute("url", "");
            model.addAttribute("colors", Color.values());
        }

        model.addAttribute("currentUser", user);

        return "User/userProfile";
    }

    @GetMapping("{username}/profile/messages")
    public String userMessages(@PathVariable String username,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {
        User user = (User) userService.loadUserByUsername(username);

        model.addAttribute("page", messagesService.findByAuthor(user.getId(), pageable));
        model.addAttribute("url", "");
        model.addAttribute("colors", Color.values());

        return userProfile(username, pageable, model);
    }

    @GetMapping("{username}/profile/edit")
    public String userEdit(@PathVariable String username, Model model) {
        return "redirect:/{username}/profile";
    }

    @PostMapping("{username}/profile")
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) Sex gender,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String dateofBirth,
            @RequestParam("userId") Long userId,
            @RequestParam("profile_pic") MultipartFile file
    ) throws IOException {

        userService.saveUser(username, password, userId, file, gender, phoneNumber, dateofBirth);

        return "redirect:/" + username + "/profile";
    }


}
