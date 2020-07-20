package com.project.MVC.model;

import com.project.MVC.model.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr_profiles")
@Getter
@Setter
@NoArgsConstructor
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sex gender = Sex.Male;

    @Column(name = "dob")
    private LocalDateTime dateOfBirth = LocalDateTime.now();

    @Column(name = "phone_number")
    private String phoneNumber = "";

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserProfile(Sex sex, LocalDateTime dateOfBirth, String phoneNumber) {
        this.gender = sex;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

}
