package com.project.MVC.controller;

import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Color;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Controller
public class MessagesController {

    private final MessagesService messagesService;

    private final UserService userService;

    public MessagesController(MessagesService messagesService, UserService userService) {
        this.messagesService = messagesService;
        this.userService = userService;
    }

    @GetMapping("/messages")
    public String getMessages(Model model,
                              @AuthenticationPrincipal User user,
                              @PageableDefault(sort = {"id"},
                                      direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", messagesService.findAll(pageable, user));
        model.addAttribute("url", "/messages");

        addMessageSendPart(model);
        return "main";
    }


    @PostMapping("/add")
    public String addMessage(@RequestParam(defaultValue = "") String title,
                             @RequestParam(defaultValue = "") String text,
                             @RequestParam("picture") MultipartFile file,
                             @RequestParam Color color,
                             @AuthenticationPrincipal User user) throws IOException {
        messagesService.createMessage(title, text, file, user, color);
        return "redirect:/messages";
    }

    @GetMapping("/messages/{id}/delete")
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

    @GetMapping("/messages/{id}")
    public String getMessage(@PathVariable Long id,
                             @AuthenticationPrincipal User currentUser,
                             Model model) {
        MessageDto message = messagesService.findById(currentUser, id);

        messagesService.addViewers(message);

        model.addAttribute("message", message);
        model.addAttribute("convertedDateMessage", messagesService.convertDate(message.getDate()));
        model.addAttribute("author", userService.findById(message.getAuthor().getId()));
        model.addAttribute("availableEdit",
                messagesService.availableEdit(message, currentUser));

        addMessageSendPart(model);

        return "messageDetail";
    }

    public static void addMessageSendPart(Model model) {
        model.addAttribute("messageSend", true);
        model.addAttribute("colors", Color.values());
    }

    @GetMapping("messages/{id}/edit")
    public String getEditForm(@PathVariable Long id,
                              @AuthenticationPrincipal User currentUser,
                              Model model) {
        MessageDto message = messagesService.findById(currentUser, id);

        model.addAttribute("message", message);

        addMessageSendPart(model);

        return messagesService.availableEdit(message, currentUser)
                ? "messageDetail" : "redirect:/messages/" + id;
    }

    @GetMapping("messages/{id}/like")
    public String like(@AuthenticationPrincipal User user,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        messagesService.like(user, id);

        UriComponents componentsBuilder = UriComponentsBuilder.fromHttpUrl(referer).build();

        componentsBuilder.getQueryParams().entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + componentsBuilder.getPath();
    }

}
