package com.example.minidoorayaccount.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    private String email;

    private String password;

    private String name;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "is_dormant")
    private Boolean isDormant;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

}
