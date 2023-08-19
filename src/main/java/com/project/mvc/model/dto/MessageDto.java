package com.project.mvc.model.dto;

import com.project.mvc.model.Message;
import com.project.mvc.model.User;
import com.project.mvc.model.enums.Color;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MessageDto {
    Long id;
    String text;
    String title;
    String announce;
    User author;
    String filename;
    Color color;
    LocalDateTime date;
    Long likes;
    Boolean meLiked;
    Long viewers;
    Integer comments;

    public MessageDto(Message message, Long likes, boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.title = message.getTitle();
        this.announce = message.getAnnounce();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.color = message.getColor();
        this.date = message.getDate();
        this.viewers = message.getViewers();
        this.comments = message.getComments().size();
        this.likes = likes;
        this.meLiked = meLiked;
    }
}
