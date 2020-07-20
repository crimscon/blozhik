package com.project.MVC.model;

import com.project.MVC.model.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long viewers = 0L;

    @Column(columnDefinition = "text", length = 300)
    private String title;

    @Column(columnDefinition = "text", length = 300)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public Message(String title, String text, User author, Color color) {
        this.date = LocalDateTime.now();
        this.title = title;

        text = text.replaceAll("\n", "<br/>");

        this.text = text;
        this.announce = createAnnounce(text);
        this.author = author;
        this.color = color;

    }

    private String createAnnounce(String text) {
        String[] textArray = text.split(" ");
        if (textArray.length == 1 || text.length() <= 300) return text.substring(0, Math.min(text.length(), 300));

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(textArray).filter(s -> (stringBuilder.length() + s.length() + 3) <= 300)
                .forEach(s -> stringBuilder.append(s).append(" "));

        stringBuilder.trimToSize();
        stringBuilder.append("...");

        return stringBuilder.toString();
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
