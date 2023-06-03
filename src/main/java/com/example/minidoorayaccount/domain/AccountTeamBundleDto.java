package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountTeamBundleDto {
    private Integer teamId;

    private Integer accountId;

    private LocalDateTime registerDate;
}
