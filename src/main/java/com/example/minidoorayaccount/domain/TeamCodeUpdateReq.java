package com.example.minidoorayaccount.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCodeUpdateReq {
    private String beforeTeamName;

    private String afterTeamName;
}
