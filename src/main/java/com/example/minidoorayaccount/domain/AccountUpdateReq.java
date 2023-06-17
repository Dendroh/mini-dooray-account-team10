package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class AccountUpdateReq {

    @NotBlank
    private String beforeEmail;
    @NotBlank
    private String afterEmail;
    private String password;
}
