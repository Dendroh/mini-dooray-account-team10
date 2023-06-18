package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountDetailsDtoImpl implements AccountDetailsDto {
    private Integer accountDetailsId;
    @NotBlank
    private String name;

    private String imageFileName;

    private Boolean isDormant;

    private LocalDateTime registerDate;
}
