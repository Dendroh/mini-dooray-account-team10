package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTeamBundleReqDto {
    private Integer teamId;

    private Integer  accountId;

    private LocalDateTime registerDate;
}
