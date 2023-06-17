package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class AccountDtoImpl implements AccountDto {
    private Integer accountId;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
