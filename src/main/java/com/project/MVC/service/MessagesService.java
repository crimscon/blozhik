package com.project.MVC.service;

import com.project.MVC.model.Message;
import com.project.MVC.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public List<Message> findByAuthor(Long userId) {
        return messagesRepository.findAll().stream().filter(message ->
                userId.equals(message.getAuthor().getId())).collect(Collectors.toList());
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
