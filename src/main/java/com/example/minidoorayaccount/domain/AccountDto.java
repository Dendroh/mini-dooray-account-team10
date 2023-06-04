package com.example.minidoorayaccount.domain;

import java.time.LocalDateTime;

public interface AccountDto {
    Integer getAccountId();

    String getEmail();

    String getPassword();

    String getName();

    String getImageFileName();

    Boolean getIsDormant();

    LocalDateTime getRegisterDate();
}
