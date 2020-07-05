package com.project.MVC.controller;

import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/messages")
    public String getMessages(Model model) {
        model.addAttribute("messages", messagesService.findAll());
        return "main";
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam String title,
                             @RequestParam String text,
                             @AuthenticationPrincipal User user) {
        Message message = new Message(title, text, user);
        messagesService.save(message);
        return "redirect:/messages";
    }

    @GetMapping("{id}/delete")
    public String deleteMessage(@PathVariable Long id) {
        messagesService.deleteById(id);
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
