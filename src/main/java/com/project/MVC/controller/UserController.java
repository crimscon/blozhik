package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.enums.Role;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<User> users = userService.getUserList(filter);

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
        userService.saveUser(username, password, form, userId);

        return "redirect:/users";
    }

    @PostMapping("delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteById(userId);

        return "redirect:/users";
    }


}
