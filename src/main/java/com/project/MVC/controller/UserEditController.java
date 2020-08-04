package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.enums.Gender;
import com.project.MVC.service.UserService;
import com.project.MVC.util.MessageUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@SessionAttributes(names = "errors")
public class UserEditController {
    private final UserService userService;

    public UserEditController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}/edit")
    public String getEditForm(@AuthenticationPrincipal User currentUser,
                              @PathVariable String username,
                              Model model) {
        User user = (User) userService.loadUserByUsername(username);

        if (currentUser.equals(user)) {
            model.addAttribute("profile", user);

            if (user.getUserProfile() != null && user.getUserProfile().getDateOfBirth() != null)
                model.addAttribute("convertedDate",
                        MessageUtil.convertDate(user.getUserProfile().getDateOfBirth()));
            model.addAttribute("url", "/edit");
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isCurrentUser", currentUser.equals(user));

            return "user/userEdit";
        } else return "redirect:/{username}/profile";
    }

    @PostMapping("{username}/edit/profile")
    public String editProfilePart(@AuthenticationPrincipal User user,
                                  @RequestParam(required = false) Gender gender,
                                  @RequestParam(required = false) String dateOfBirth,
                                  @PathVariable String username) {
        userService.changeProfilePart(user, userService.getUserByUsername(username), gender, dateOfBirth);

        return "redirect:/{username}/edit";
    }

    @PostMapping("{username}/edit/email")
    public String editEmail(@AuthenticationPrincipal User user,
                            @RequestParam String email,
                            @PathVariable String username) {
        userService.changeEmail(user, userService.getUserByUsername(username), email);

        return "redirect:/{username}/edit";
    }

    @PostMapping("{username}/edit/phone")
    public String editPhone(@AuthenticationPrincipal User user,
                            @RequestParam String tel,
                            @PathVariable String username) {
        userService.changePhone(user, userService.getUserByUsername(username), tel);

        return "redirect:/{username}/edit";
    }

    @PostMapping("{username}/edit/picture")
    public String editPicture(@AuthenticationPrincipal User user,
                              @RequestParam("profile_pic") MultipartFile file,
                              @PathVariable String username) throws IOException {
        userService.editPicture(user, userService.getUserByUsername(username), file);

        return "redirect:/{username}/edit";
    }

    @GetMapping("{username}/edit/picture/delete")
    public String deletePicture(@AuthenticationPrincipal User user,
                                @PathVariable String username) throws IOException {
        userService.deletePicture(user, userService.getUserByUsername(username));

        return "redirect:/{username}/edit";
    }

    @PostMapping("{username}/edit/password")
    public String editPassword(@AuthenticationPrincipal User user,
                               @RequestParam String oldPassword,
                               @RequestParam String newPassword,
                               @RequestParam String newPasswordConfirm,
                               @PathVariable String username) {
        userService.changePassword(user, userService.getUserByUsername(username), oldPassword, newPassword, newPasswordConfirm);

        return "redirect:/{username}/edit";
    }
}
