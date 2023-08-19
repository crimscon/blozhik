package com.project.mvc.model;

import com.project.mvc.model.enums.Color;
import com.project.mvc.util.MessageUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
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
    @CreationTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "message")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public Message(String title, String text, User author, Color color) {
        this.title = title;
        this.text = MessageUtil.createText(text);
        this.announce = MessageUtil.createAnnounce(this.getText());
        this.author = author;
        this.color = color;
    }
}
