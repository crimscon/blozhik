package com.project.MVC.controller;

import com.project.MVC.model.Color;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.service.MessagesService;
import com.project.MVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UserService userService;

    @GetMapping("/messages")
    public String getMessages(Model model,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", messagesService.findAll(pageable));
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
    public String deleteMessage(@PathVariable Long id) throws IOException {
        return messagesService.deleteMessage(id) ? "redirect:/messages" : "redirect:/messages/" + id;
    }

    @PostMapping("messages/{id}/edit")
    public String editMessage(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String text) {
        messagesService.editMessage(id, title, text);

        return "redirect:/messages/" + id;
    }

    @GetMapping("/messages/{id}")
    public String getMessage(@PathVariable Long id, Model model) {
        Message message = messagesService.findById(id);
        model.addAttribute("message", message);
        model.addAttribute("author", userService.findById(message.getAuthor().getId()));
        model.addAttribute("edit", false);
        model.addAttribute("availableEdit", messagesService.availableEdit(message));

        addMessageSendPart(model);

        return "messageDetail";
    }

    private void addMessageSendPart(Model model) {
        model.addAttribute("colors", Color.values());
    }

    @GetMapping("messages/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model) {
        Message message = messagesService.findById(id);

        model.addAttribute("message", message);
        model.addAttribute("edit", true);

        addMessageSendPart(model);

        return messagesService.availableEdit(message) ? "messageDetail" : "redirect:/messages/" + id;
    }

}
