package com.example.minidoorayaccount.domain;

import com.example.minidoorayaccount.entity.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountDetailsDtoImpl {
    private Integer accountDetailsId;

    private Account account;

    private String name;

    private String imageFileName;

    private Boolean isDormant;

    private LocalDateTime registerDate;
}
