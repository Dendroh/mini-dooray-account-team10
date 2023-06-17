package com.example.minidoorayaccount.domain;

import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;

import java.time.LocalDateTime;

public interface AccountTeamBundleDto {
    AccountTeamBundle.Pk getPk();

    TeamCode getTeamCode();

    AccountDetails getAccountDetails();

    LocalDateTime getRegisterDate();
}
