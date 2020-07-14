package com.project.MVC.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "text", length = 300)
    private String title;

    @Column(columnDefinition = "text", length = 300)
    private String announce;

    @Column(columnDefinition = "text")
    private String text;

    private String filename;
    private Color color;
    private Date date;

    private User author;

    public Message(String title, String text, User author, Color color) {
        this.date = new Date();
        this.title = title;
        this.text = text;
        this.announce = createAnnounce(text);
        this.author = author;
        this.color = color;
    }

    private String createAnnounce(String text) {
        String[] textArray = text.split(" ");
        if (textArray.length == 1) return text.substring(0, Math.min(text.length(), 300));

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : textArray)
            if ((stringBuilder.length() + s.length() + 3) <= 300) {
                stringBuilder.append(s).append(" ");
            }

        stringBuilder.trimToSize();
        stringBuilder.append("...");

        return stringBuilder.toString();
    }
}
