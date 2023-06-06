package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDto;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.TeamCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamCodeRepository extends JpaRepository<TeamCode, Integer>, TeamCodeRepositoryCustom {
    List<TeamCodeDto> findAllBy();

    @Override
    <S extends TeamCode> S save(S entity);

    TeamCodeDto getTeamCodeByTeamId(Integer teamId);

    TeamCodeDto getTeamCodeByTeamNameLike(String teamName);

    TeamCode findByTeamId(Integer teamId);

    TeamCode findByTeamName(String teamName);

    TeamCodeDtoImpl queryByTeamId(Integer teamCodeId);

    TeamCodeDtoImpl queryByTeamName(String teamName);
}
