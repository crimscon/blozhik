package com.project.MVC.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 300)
    private String title, smallText;

    @Column(length = 500)
    private String text;

    private User author;

    public Message(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }
}
