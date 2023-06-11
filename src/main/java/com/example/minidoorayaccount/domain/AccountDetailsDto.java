package com.example.minidoorayaccount.domain;

import java.time.LocalDateTime;

public interface AccountDetailsDto {
    Integer getAccountDetailsId();
    String getName();

    String getImageFileName();

    Boolean getIsDormant();

    LocalDateTime getRegisterDate();
}
