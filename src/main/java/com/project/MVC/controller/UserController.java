package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Gender;
import com.project.MVC.model.enums.Role;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import com.project.MVC.util.ControllerUtil;
import com.project.MVC.util.MessageUtil;
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

import java.io.IOException;
import java.util.List;

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
                    MessageUtil.convertDate(user.getUserProfile().getDateOfBirth()));
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("url", "/profile");
        model.addAttribute("messagesCount", messagesService.findUserMessageCount(user));
        model.addAttribute("likedCount", messagesService.findUserLikes(user));

        ControllerUtil.addModelSubsPart(model, user, currentUser);
        ControllerUtil.addMessageSendPart(model);

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

        ControllerUtil.addModelSubsPart(model, user, currentUser);
        ControllerUtil.addMessageSendPart(model);

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

        ControllerUtil.addModelSubsPart(model, user, currentUser);
        ControllerUtil.addMessageSendPart(model);

        status.setComplete();

        return "user/userMessages";
    }

    @GetMapping("{username}/subscribe")
    public String subscribe(@PathVariable String username,
                            @AuthenticationPrincipal User currentUser) {
        userService.subscribe(userService.getUserByUsername(username), currentUser);

        return "redirect:/{username}/profile";
    }

    @GetMapping("{username}/unsubscribe")
    public String unsubscribe(@PathVariable String username,
                              @AuthenticationPrincipal User currentUser) {
        userService.unsubscribe(userService.getUserByUsername(username), currentUser);

        return "redirect:/{username}/profile";
    }

    @GetMapping("{username}/{type}/list")
    public String subsList(@PathVariable String username,
                           @PathVariable String type,
                           @AuthenticationPrincipal User currentUser,
                           Model model) {
        User user = userService.getUserByUsername(username);

        if ("subscribers".equals(type)) {
            model.addAttribute("subs", user.getSubscribers());
        } else {
            model.addAttribute("subs", user.getSubscriptions());
        }

        model.addAttribute("profile", user);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("url", type);

        ControllerUtil.addModelSubsPart(model, user, currentUser);
        ControllerUtil.addMessageSendPart(model);

        return "user/subsList";
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
        model.addAttribute("profile", user);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("roles", Role.values());
        if (user.getUserProfile() != null && user.getUserProfile().getDateOfBirth() != null)
            model.addAttribute("convertedDate",
                    MessageUtil.convertDate(user.getUserProfile().getDateOfBirth()));

        return "user/admin/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id,
                             @AuthenticationPrincipal User currentUser) throws IOException {
        userService.deleteUser(userService.findById(id), currentUser);

        return "redirect:/users";
    }

}
