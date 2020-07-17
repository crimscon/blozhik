package com.project.MVC.service;

import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Color;
import com.project.MVC.repository.MessagesRepository;
import com.project.MVC.util.ThumbnailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public void createMessage(String title,
                              String text,
                              MultipartFile file,
                              User user,
                              Color color) throws IOException {

        Message message = new Message(title, text, user, color);

        if (file != null && !file.getOriginalFilename().isEmpty()) {

            String filename = ThumbnailUtil.createFile(file, uploadPath, true);

            message.setFilename(filename);
        }

        if (message.getFilename() != null ||
                !message.getTitle().isEmpty() ||
                !message.getText().isEmpty()) {
            messagesRepository.save(message);
        } else ThumbnailUtil.deleteIfExistFile(uploadPath, message.getFilename());
    }


    public Message findById(Long id) {
        return messagesRepository.getOne(id);
    }

    public boolean deleteMessage(Long id, User currentUser) throws IOException {
        Message message = findById(id);

        if (currentUser.getId().equals(message.getAuthor().getId())) {
            if (message.getFilename() != null) {
                ThumbnailUtil.deleteIfExistFile(uploadPath, message.getFilename());
            }

            messagesRepository.delete(message);

            return true;
        }
        return false;
    }

    public boolean availableEdit(Message message, User currentUser) {
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

        messagesRepository.save(message);
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User author, User currentUser) {
        return messagesRepository.findByUser(pageable, author, currentUser);
    }

    public Page<MessageDto> findAll(Pageable pageable, User user) {
        return messagesRepository.findAll(pageable, user);
    }
}
