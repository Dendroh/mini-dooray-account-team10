package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountDetailsPostReq {

    @NotBlank
    private String accountEmail;

    private String name;
}
