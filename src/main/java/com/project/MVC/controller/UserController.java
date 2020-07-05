package com.project.MVC.controller;

import com.project.MVC.model.Role;
import com.project.MVC.model.User;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<User> users;

        if (filter != null && !filter.isEmpty()) {
            users = new ArrayList<>();
            users.add((User) userService.loadUserByUsername(filter));
        } else {
            users = userService.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("filter", filter);

        return "User/userList";
    }

    @GetMapping("{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "User/userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") Long userId
    ) {
        User user = userService.findById(userId);
        user.setUsername(username);
        user.setPassword(password);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.save(user);

        return "redirect:/users";
    }

    @PostMapping("delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteById(userId);

        return "redirect:/users";
    }


}
