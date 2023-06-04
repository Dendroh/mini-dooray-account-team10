package com.example.minidoorayaccount.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TeamCodeDtoImpl {

    private Integer teamId;

    private String teamName;
}
