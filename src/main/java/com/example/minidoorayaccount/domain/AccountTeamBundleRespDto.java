package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTeamBundleRespDto {
    private Integer teamId;

    private Integer  accountId;

    private LocalDateTime registerDate;

    private String teamName;

    private String accountEmail;
}
