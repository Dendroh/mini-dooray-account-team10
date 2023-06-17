package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountTeamCodeBundlePostReq {
    @NotBlank
    private String email;

    @NotBlank
    private String teamName;

}
