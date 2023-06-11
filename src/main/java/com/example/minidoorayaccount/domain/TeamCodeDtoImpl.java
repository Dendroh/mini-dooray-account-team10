package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class TeamCodeDtoImpl implements TeamCodeDto {
    private Integer teamId;

    @NotBlank
    private String teamName;
}
