package com.project.MVC.model.dto;

import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.model.enums.Color;
import lombok.Getter;

import java.util.Date;

@Getter
public class MessageDto {
    private final Long id;
    private final String text;
    private final String title;
    private final String announce;
    private final User author;
    private final String filename;
    private final Color color;
    private final Date date;
    private final Long likes;
    private final Boolean meLiked;

    public MessageDto(Message message, Long likes, boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.title = message.getTitle();
        this.announce = message.getAnnounce();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.color = message.getColor();
        this.date = message.getDate();
        this.likes = likes;
        this.meLiked = meLiked;
    }
}
