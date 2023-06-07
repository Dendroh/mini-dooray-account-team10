package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountTeamBundleUpdateRequest {

    @NotNull
    Integer accountId;

    @NotNull
    Integer teamId;

    Integer newAccountId;

    Integer newTeamId;
}
