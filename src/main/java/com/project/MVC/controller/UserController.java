package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Role;
import com.project.MVC.model.enums.Sex;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(names = "errors")
public class UserController {

    private final UserService userService;
    private final MessagesService messagesService;

    public UserController(UserService userService, MessagesService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
    }

    @GetMapping("{username}/profile")
    public String getProfile(@AuthenticationPrincipal User currentUser,
                             @PathVariable String username,
                             SessionStatus status,
                             Model model) {
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("profile", user);
        if (user.getUserProfile() != null && user.getUserProfile().getDateOfBirth() != null)
            model.addAttribute("convertedDate",
                    messagesService.convertDate(user.getUserProfile().getDateOfBirth()));
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("url", "/profile");

        MessagesController.addMessageSendPart(model);

        status.setComplete();

        return "user/userProfile";
    }

    @GetMapping("{username}/messages")
    public String getUserMessages(@AuthenticationPrincipal User currentUser,
                                  @PathVariable String username,
                                  SessionStatus status,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model) {
        User user = (User) userService.loadUserByUsername(username);

        Page<MessageDto> page = messagesService.messageListForUser(pageable, user, currentUser);

        model.addAttribute("profile", user);
        model.addAttribute("page", page);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("url", "/messages");

        MessagesController.addMessageSendPart(model);

        status.setComplete();

        return "user/userMessages";
    }

    @GetMapping("{username}/liked")
    public String getLikedMessages(@AuthenticationPrincipal User currentUser,
                                  @PathVariable String username,
                                   SessionStatus status,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model) {
        User user = (User) userService.loadUserByUsername(username);

        Page<MessageDto> page = messagesService.findWhereMeLiked(pageable, user);

        model.addAttribute("profile", user);
        model.addAttribute("page", page);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("url", "/liked");

        MessagesController.addMessageSendPart(model);

        status.setComplete();

        return "user/userMessages";
    }

    @GetMapping("{username}/edit")
    public String getEditForm(@AuthenticationPrincipal User currentUser,
                              @PathVariable String username,
                              Model model) {
        User user = (User) userService.loadUserByUsername(username);

        if (currentUser.equals(user)) {
            model.addAttribute("profile", currentUser);

            if (currentUser.getUserProfile() != null && currentUser.getUserProfile().getDateOfBirth() != null)
                model.addAttribute("convertedDate",
                        messagesService.convertDate(currentUser.getUserProfile().getDateOfBirth()));
            model.addAttribute("url", "/edit");
            model.addAttribute("genders", Sex.values());
            model.addAttribute("isCurrentUser", currentUser.equals(user));

            return "user/userEdit";
        } else return "redirect:/{username}/profile";
    }

    @PostMapping("{username}/edit")
    public String saveEditForm(@RequestParam String password,
                               @RequestParam(required = false) Sex gender,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) String dateOfBirth,
                               @RequestParam String email,
                               @RequestParam("profile_pic") MultipartFile file,
                               @AuthenticationPrincipal User user) throws IOException {
        userService.saveUser(user, email, password, file, gender, phoneNumber, dateOfBirth);

        return "redirect:/{username}/profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public String userList(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<User> users = userService.getUserList(filter);

        model.addAttribute("users", users);
        model.addAttribute("filter", filter);

        return "user/admin/userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "user/admin/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") Long userId
    ) {
        userService.saveUser(username, password, form, userId);

        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) throws IOException {
        userService.deleteUser(userService.findById(id));

        return "redirect:/users";
    }

}
