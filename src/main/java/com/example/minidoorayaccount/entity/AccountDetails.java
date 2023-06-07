package com.example.minidoorayaccount.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_details")
@Getter
@Setter
@EqualsAndHashCode
public class AccountDetails implements Serializable {
    @Id
    @Column(name = "account_details_id")
    private Integer accountDetailsId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_details_id", referencedColumnName = "account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId(value = "accountDetailsId")
    private Account account;

    private String name;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "is_dormant")
    private Boolean isDormant;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

}
