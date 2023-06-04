package com.example.minidoorayaccount.domain;

import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;

import java.time.LocalDateTime;

public interface AccountTeamBundleDto {
    AccountTeamBundle.Pk getPk();

    TeamCode getTeamCode();

    Account getAccount();

    LocalDateTime getRegisterDate();
}
