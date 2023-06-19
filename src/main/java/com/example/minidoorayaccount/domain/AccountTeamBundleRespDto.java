package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTeamBundleRespDto {
    private Integer teamId;

    private Integer  accountId;

    private LocalDateTime registerDate;

    private String teamName;

    private String accountEmail;
}
