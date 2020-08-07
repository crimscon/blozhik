package com.project.MVC.model;

import com.project.MVC.model.enums.Gender;
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
    private Gender gender = Gender.MALE;

    @Column(name = "dob")
    private LocalDateTime dateOfBirth = LocalDateTime.now();

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserProfile(Gender gender, LocalDateTime dateOfBirth, String phoneNumber) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public UserProfile(User user) {
        this.user = user;
    }
}
