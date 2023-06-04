package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class AccountDtoImpl {
    private Integer accountId;

    private String email;

    private String password;

    private String name;

    private String imageFileName;

    private Boolean isDormant;

    private LocalDateTime registerDate;
}
