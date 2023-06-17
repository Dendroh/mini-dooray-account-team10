package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountTeamBundleUpdateReq {
    @NotBlank
    String email;
    @NotBlank
    String teamName;

    String newEmail;

    String newTeamName;
}
