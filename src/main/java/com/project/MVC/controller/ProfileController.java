package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessagesService messagesService;

    @Value("${upload.path}")
    private String uploadPath;

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
            @RequestParam("userId") Long userId,
            @RequestParam("profile_pic") MultipartFile file
    ) throws IOException {

        User user = userService.findById(userId);
        user.setUsername(username);
        user.setPassword(password);

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            Files.deleteIfExists(Paths.get(new File(uploadPath + "/" + user.getProfile_pic()).getPath()));
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + filename));

            user.setProfile_pic(filename);
        }

        userService.save(user);

        return "redirect:/{username}/profile";
    }


}
