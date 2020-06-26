package com.project.MVC.controller;

import com.project.MVC.model.Message;
import com.project.MVC.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessagesController {

    @Autowired
    private MessagesRepository messagesRepository;

    @GetMapping("/")
    public String getMessages(Model model) {
        model.addAttribute("messages", messagesRepository.findAll());
        return "main";
    }

    @PostMapping("/add")
    public String addMessage(@RequestParam String title,
                              @RequestParam String text) {
        Message message = new Message(title, text);
        messagesRepository.save(message);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteMessage(@RequestParam Long id) {
        messagesRepository.deleteById(id);
        return "redirect:/";
    }


    //TODO посмотреть как сделать редактирование
    //TODO разобраться с textarea
    @PostMapping("/edit")
    public String editMessage(@RequestParam Long id,
                              @RequestParam String title,
                              @RequestParam String text) {
        Message message = messagesRepository.findById(id).get();

        if (!message.getText().equals(text)) {
            message.setText(text);
        }

        if (!message.getTitle().equals(title)) {
            message.setTitle(title);
        }

        messagesRepository.save(message);

        return "redirect:/";
    }

}
