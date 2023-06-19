package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TeamCodeUpdateReq {

    @NotBlank
    private String beforeTeamName;

    @NotBlank
    private String afterTeamName;
}
