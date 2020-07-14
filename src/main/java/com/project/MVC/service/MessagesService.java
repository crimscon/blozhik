package com.project.MVC.service;

import com.project.MVC.model.Color;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.repository.MessagesRepository;
import com.project.MVC.util.ThumbnailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Page<Message> findByAuthor(Long userId, Pageable pageable) {
        List<Message> messages = messagesRepository.findAll().stream().
                filter(message -> message.getAuthor().getId().equals(userId)).
                collect(Collectors.toList());

        int start = (int) pageable.getOffset(),
                end = Math.min((start + pageable.getPageSize()), messages.size());

        return new PageImpl<Message>(messages.subList(start, end), pageable, messages.size());

    }

    public void createMessage(String title,
                              String text,
                              MultipartFile file,
                              User user,
                              Color color) throws IOException {

        Message message = new Message(title, text, user, color);

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + filename));
            ThumbnailUtil.createThumbnail(uploadPath + "/" + filename);

            message.setFilename(filename);
        }

        if (message.getFilename() != null ||
                !message.getTitle().isEmpty() ||
                !message.getText().isEmpty()) {
            save(message);
        } else Files.deleteIfExists(Paths.get(new File(uploadPath + "/" + message.getFilename()).getPath()));
    }



    public <S extends Message> void save(S s) {
        messagesRepository.save(s);
    }

    public Message findById(Long aLong) {
        return messagesRepository.findById(aLong).get();
    }

    public Page<Message> findAll(Pageable pageable) {
        return messagesRepository.findAll(pageable);
    }

    public void deleteById(Long aLong) {
        messagesRepository.deleteById(aLong);
    }

    public void delete(Message message) {
        messagesRepository.delete(message);
    }

    public boolean deleteMessage(Long id) throws IOException {
        Message message = findById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getId().equals(message.getAuthor().getId())) {
            if (message.getFilename() != null) {
                Files.deleteIfExists(Paths.get(new File(uploadPath + "/" + message.getFilename()).getPath()));
            }

            delete(message);

            return true;
        }
        return false;
    }

    public boolean availableEdit(Message message) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 20);

        return message.getDate() != null
                && message.getAuthor().getId().equals(currentUser.getId())
                && !message.getDate().after(calendar.getTime());
    }

    public void editMessage(Long id, String title, String text) {
        Message message = findById(id);

        if (!message.getText().equals(text)) {
            message.setText(text);
        }

        if (!message.getTitle().equals(title)) {
            message.setTitle(title);
        }

        save(message);
    }
}
