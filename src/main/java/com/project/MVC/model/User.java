package com.project.MVC.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;
}
