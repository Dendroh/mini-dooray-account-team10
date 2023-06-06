package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.QTeamCode;
import com.example.minidoorayaccount.entity.TeamCode;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TeamCodeRepositoryImpl extends QuerydslRepositorySupport implements TeamCodeRepositoryCustom {

    public TeamCodeRepositoryImpl() {
        super(TeamCode.class);
    }

    @Override
    public void updateTeamCode(TeamCodeDtoImpl teamCodeDto) {
        QTeamCode teamCode = QTeamCode.teamCode;

        update(teamCode)
                .set(teamCode.teamName, teamCodeDto.getTeamName())
                .where(teamCode.teamId.eq(teamCodeDto.getTeamId()))
                .execute();
    }

    @Override
    public void deleteTeamCode(Integer deleteTeamCodeId) {
        QTeamCode teamCode = QTeamCode.teamCode;

        delete(teamCode)
                .where(teamCode.teamId.eq(deleteTeamCodeId))
                .execute();
    }
}
