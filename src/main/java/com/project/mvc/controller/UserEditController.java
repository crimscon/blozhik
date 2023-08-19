package com.project.mvc.controller;

import com.project.mvc.model.User;
import com.project.mvc.model.enums.Gender;
import com.project.mvc.service.UserService;
import com.project.mvc.util.ControllerUtil;
import com.project.mvc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@Controller
@SessionAttributes(names = "errors")
@RequiredArgsConstructor
public class UserEditController {
    private final UserService userService;

    @GetMapping("{username}/edit")
    public String getEditForm(@AuthenticationPrincipal User currentUser,
                              @PathVariable String username,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              @RequestHeader(required = false) String referer) {
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
                                  @PathVariable String username,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer) {
        userService.changeProfilePart(user, userService.getUserByUsername(username), gender, dateOfBirth);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @PostMapping("{username}/edit/email")
    public String editEmail(@AuthenticationPrincipal User user,
                            @RequestParam String email,
                            @PathVariable String username,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer) {
        userService.changeEmail(user, userService.getUserByUsername(username), email);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @PostMapping("{username}/edit/phone")
    public String editPhone(@AuthenticationPrincipal User user,
                            @RequestParam String tel,
                            @PathVariable String username,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer) {
        userService.changePhone(user, userService.getUserByUsername(username), tel);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @PostMapping("{username}/edit/picture")
    public String editPicture(@AuthenticationPrincipal User user,
                              @RequestParam("profile_pic") MultipartFile file,
                              @PathVariable String username,
                              RedirectAttributes redirectAttributes,
                              @RequestHeader(required = false) String referer) throws IOException {
        userService.editPicture(user, userService.getUserByUsername(username), file);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @GetMapping("{username}/edit/picture/delete")
    public String deletePicture(@AuthenticationPrincipal User user,
                                @PathVariable String username,
                                RedirectAttributes redirectAttributes,
                                @RequestHeader(required = false) String referer) throws IOException {
        userService.deletePicture(user, userService.getUserByUsername(username));

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @PostMapping("{username}/edit/password")
    public String editPassword(@AuthenticationPrincipal User user,
                               @RequestParam(defaultValue = "", required = false) String oldPassword,
                               @RequestParam String newPassword,
                               @RequestParam(defaultValue = "", required = false) String newPasswordConfirm,
                               @PathVariable String username,
                               RedirectAttributes redirectAttributes,
                               @RequestHeader(required = false) String referer) {
        userService.changePassword(user, userService.getUserByUsername(username), oldPassword, newPassword, newPasswordConfirm);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("/users/{id}/roles")
    public String editRoles(@AuthenticationPrincipal User user,
                            @PathVariable Long id,
                            @RequestParam Map<String, String> form,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer) {
        userService.changeRoles(user, userService.findById(id), form);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }
}
