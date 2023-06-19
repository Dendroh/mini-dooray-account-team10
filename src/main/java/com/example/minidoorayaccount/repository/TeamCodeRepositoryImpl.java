package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.QTeamCode;
import com.example.minidoorayaccount.entity.TeamCode;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class TeamCodeRepositoryImpl extends QuerydslRepositorySupport implements TeamCodeRepositoryCustom {

    public TeamCodeRepositoryImpl() {
        super(TeamCode.class);
    }

    @Override
    @Transactional
    public void updateTeamCode(TeamCodeDtoImpl teamCodeDto) {
        QTeamCode teamCode = QTeamCode.teamCode;

        TeamCode updateTeamCode = from(teamCode)
                .where(teamCode.teamId.eq(teamCodeDto.getTeamId()))
                        .fetchFirst();

        updateTeamCode.setTeamName(teamCodeDto.getTeamName());
    }
}
