package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;

public interface TeamCodeRepositoryCustom {
    void updateTeamCode(TeamCodeDtoImpl teamCode);

    void deleteTeamCode(Integer deleteTeamCodeId);
}
