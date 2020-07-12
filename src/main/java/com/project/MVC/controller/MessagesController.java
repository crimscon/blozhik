package com.project.MVC.controller;

import com.project.MVC.model.Color;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.service.MessagesService;
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

    @GetMapping("/messages")
    public String getMessages(Model model,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", messagesService.findAll(pageable));
        model.addAttribute("colors", Color.values());
        model.addAttribute("url", "/messages");
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

    @GetMapping("{id}/delete")
    public String deleteMessage(@PathVariable Long id) throws IOException {
        messagesService.deleteMessage(id);
        return "redirect:/messages";
    }


    //TODO посмотреть как сделать редактирование
    //TODO разобраться с textarea
    @PostMapping("messages/edit")
    public String editMessage(@RequestParam Long id,
                              @RequestParam String title,
                              @RequestParam String text) {
        Message message = messagesService.findById(id);

        if (!message.getText().equals(text)) {
            message.setText(text);
        }

        if (!message.getTitle().equals(title)) {
            message.setTitle(title);
        }

        messagesService.save(message);

        return "redirect:/messages";
    }

}
