package com.project.MVC.controller;

import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Color;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import com.project.MVC.util.ControllerUtil;
import com.project.MVC.util.MessageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@SessionAttributes(names = {"errors", "errorMessage", "invalids", "invalidComment"})
public class MessagesController {

    private final MessagesService messagesService;

    private final UserService userService;

    public MessagesController(MessagesService messagesService, UserService userService) {
        this.messagesService = messagesService;
        this.userService = userService;
    }

    @GetMapping("/changeFeed")
    public String changeFeed(HttpServletRequest request, HttpServletResponse response) {
        messagesService.changeFeed(request, response);
        return "redirect:/messages";
    }

    @GetMapping("/messages")
    public String getMessages(HttpServletRequest request, HttpServletResponse response,
                                 @CookieValue(name = "typeMessage", required = false, defaultValue = "all") String type,
                                 Model model,
                                 @AuthenticationPrincipal User user,
                                 SessionStatus status,
                                 @PageableDefault(sort = {"id"},
                                         direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("page", messagesService.getMessages(request, response,
                (User) userService.loadUserByUsername(user.getUsername()), pageable));
        model.addAttribute("messageType", type);
        model.addAttribute("url", "/messages");

        ControllerUtil.addMessageSendPart(model);

        status.setComplete();

        return "main";
    }

    @PostMapping("/add")
    public String addMessage(RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer,
                             @Valid Message message,
                             BindingResult bindingResult,
                             @RequestParam("picture") MultipartFile file,
                             @RequestParam Color color,
                             @AuthenticationPrincipal User user,
                             Model model) throws IOException {
        if (ControllerUtil.hasErrors(bindingResult, message, file)) {

            ControllerUtil.addErrorsToModel(bindingResult, message, file, model);

        } else {
            messagesService.createMessage(message, file, user, color);
        }

        return ControllerUtil.createRedirect(redirectAttributes, referer);

    }

    @GetMapping("/message/{id}/delete")
    public String deleteMessage(@PathVariable Long id,
                                @AuthenticationPrincipal User user) throws IOException {
        return messagesService.deleteMessage(id, user) ? "redirect:/messages" : "redirect:/messages/" + id;
    }

    @PostMapping("messages/{id}/edit")
    public String editMessage(@AuthenticationPrincipal User user,
                              @PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String text,
                              @RequestParam Color color) {
        messagesService.editMessage(user, id, title, text, color);

        return "redirect:/messages/" + id;
    }

    @GetMapping("/message/{id}")
    public String getMessage(@PathVariable Long id,
                             SessionStatus status,
                             @AuthenticationPrincipal User currentUser,
                             Model model,
                             @PageableDefault(sort = {"id"},
                                     direction = Sort.Direction.DESC) Pageable pageable) {
        MessageDto message = messagesService.findById(currentUser, id);
        messagesService.addViewers(message);

        model.addAttribute("message", message);
        model.addAttribute("convertedDateMessage", MessageUtil.convertDate(message.getDate()));
        model.addAttribute("author", userService.findById(message.getAuthor().getId()));
        model.addAttribute("comments", messagesService.findCommentsByMessage(messagesService.findById(id), pageable));
        model.addAttribute("url", "/messages/" + id);
        model.addAttribute("availableEdit",
                messagesService.availableEdit(message, currentUser));

        ControllerUtil.addMessageSendPart(model);

        status.setComplete();

        return "messageDetail";
    }

    @GetMapping("message/{id}/edit")
    public String getEditForm(@PathVariable Long id,
                              @AuthenticationPrincipal User currentUser,
                              Model model) {
        MessageDto message = messagesService.findById(currentUser, id);

        model.addAttribute("message", message);

        ControllerUtil.addMessageSendPart(model);

        return messagesService.availableEdit(message, currentUser)
                ? "messageDetail" : "redirect:/messages/" + id;
    }

    @GetMapping("message/{id}/like")
    public String like(@AuthenticationPrincipal User user,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        messagesService.like(user, id);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

}
