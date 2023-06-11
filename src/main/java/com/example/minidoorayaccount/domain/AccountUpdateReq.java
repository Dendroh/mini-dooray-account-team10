package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountUpdateReq {
    private String beforeEmail;
    private String afterEmail;
    private String password;
}
