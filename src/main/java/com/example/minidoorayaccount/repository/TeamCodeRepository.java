package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDto;
import com.example.minidoorayaccount.entity.TeamCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamCodeRepository extends JpaRepository<TeamCode, Integer> {
    List<TeamCodeDto> findAllBy();

    @Override
    <S extends TeamCode> S save(S entity);
}
