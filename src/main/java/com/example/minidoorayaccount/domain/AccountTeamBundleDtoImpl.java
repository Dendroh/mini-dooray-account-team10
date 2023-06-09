package com.example.minidoorayaccount.domain;

import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class AccountTeamBundleDtoImpl implements AccountTeamBundleDto {
    private AccountTeamBundle.Pk pk;

    private AccountDetails accountDetails;

    private TeamCode teamCode;

    private LocalDateTime registerDate;

}
