package com.project.MVC.model;

import com.project.MVC.model.enums.Color;
import com.project.MVC.util.MessageUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long viewers = 0L;

    @Length(max = 300, message = "Заголовок не может быть больше 300 символов")
    @Column(columnDefinition = "text")
    private String title;

    @Column(columnDefinition = "text")
    private String announce;

    @Column(columnDefinition = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private Color color;

    @CreatedDate
    private LocalDateTime date;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany
    @JoinTable(
            name = "comments",
            joinColumns = { @JoinColumn(name = "message_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
    )
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public Message(String title, String text, User author, Color color) {
        this.date = LocalDateTime.now();
        this.title = title;
        this.text = MessageUtil.createText(text);
        this.announce = MessageUtil.createAnnounce(this.getText());
        this.author = author;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
