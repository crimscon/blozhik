package com.project.MVC.service;

import com.project.MVC.model.Color;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Message> findByAuthor(Long userId) {
        return messagesRepository.findAll().stream().filter(message ->
                userId.equals(message.getAuthor().getId())).collect(Collectors.toList());
    }

    public void createMessage(String title,
                              String text,
                              MultipartFile file,
                              User user,
                              Map<String, String> form) throws IOException {

        String colorValue = Color.LIGHT.name().toLowerCase();
        Set<String> colors = Arrays.stream(Color.values())
                .map(Color::name)
                .collect(Collectors.toSet());

        for (String key : form.keySet()) {
            if (colors.contains(key.toUpperCase())) colorValue = key;
        }

        Message message = new Message(title, text, user, colorValue);

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            Files.deleteIfExists(Paths.get(new File(uploadPath + "/" + message.getFilename()).getPath()));
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + filename));

            message.setFilename(filename);
        }

        if (message.getFilename() != null ||
                !message.getTitle().isEmpty() ||
                !message.getText().isEmpty()) {
            save(message);
        }
    }

    public <S extends Message> void save(S s) {
        messagesRepository.save(s);
    }

    public Message findById(Long aLong) {
        return messagesRepository.findById(aLong).get();
    }

    public List<Message> findAll() {
        return messagesRepository.findAll();
    }

    public void deleteById(Long aLong) {
        messagesRepository.deleteById(aLong);
    }

    public void delete(Message message) {
        messagesRepository.delete(message);
    }
}
