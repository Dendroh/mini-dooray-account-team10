package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;

public interface TeamCodeService {

    TeamCodeDtoImpl getTeamCodeByTeamId(Integer teamId);

    TeamCodeDtoImpl getTeamCodeByTeamName(String teamName);

    TeamCodeDtoImpl createTeamCode(TeamCodeDtoImpl newTeamCode);

    TeamCodeDtoImpl updateTeamCodeById(TeamCodeDtoImpl updateTeamCode);

    Integer deleteTeamCodeById(Integer deleteTeamId);
}
