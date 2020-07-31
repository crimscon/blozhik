package com.project.MVC.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotBlank(message = "Комментарий не может быть пустым")
    @Length(max = 1000, message = "Комментарий не должен быть больше 1000 символов")
    @Column(columnDefinition = "text")
    private String text;

    @CreatedDate
    private LocalDateTime date = LocalDateTime.now();

}
