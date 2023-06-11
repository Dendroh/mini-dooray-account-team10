package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.domain.TeamCodeUpdateReq;

public interface TeamCodeService {

    TeamCodeDtoImpl getTeamCodeByTeamId(Integer teamId);

    TeamCodeDtoImpl getTeamCodeByTeamName(String teamName);

    TeamCodeDtoImpl createTeamCode(TeamCodeDtoImpl newTeamCode);

    TeamCodeDtoImpl updateTeamCodeById(TeamCodeUpdateReq updateTeamCode);

    void deleteTeamCodeById(String deleteTeamName);
}
