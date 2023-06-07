package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.TeamCodeDto;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.TeamCode;
import com.example.minidoorayaccount.exception.NotFoundTeamCodeException;
import com.example.minidoorayaccount.repository.TeamCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultTeamCodeService implements TeamCodeService{
    private final TeamCodeRepository repository;

    public TeamCodeDtoImpl getTeamCodeByTeamId(Integer teamId) {
        TeamCodeDto teamCodeDto = repository.getTeamCodeByTeamId(teamId);

        if (Objects.isNull(teamCodeDto))
            throw new NotFoundTeamCodeException();

        return converterToDtoImpl(teamCodeDto);
    }

    @Override
    public TeamCodeDtoImpl getTeamCodeByTeamName(String teamName) {
        TeamCodeDto teamCodeDto = repository.getTeamCodeByTeamNameLike(teamName);

        if (Objects.isNull(teamCodeDto))
            throw new NotFoundTeamCodeException();

        return converterToDtoImpl(teamCodeDto);
    }

    @Override
    public TeamCodeDtoImpl createTeamCode(TeamCodeDtoImpl newTeamCode) {
        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName(newTeamCode.getTeamName());
        return converterToDtoImpl(repository.saveAndFlush(teamCode));
    }

    @Override
    @Transactional
    public TeamCodeDtoImpl updateTeamCodeById(TeamCodeDtoImpl updateTeamCode) {
        TeamCode teamCode = repository.findByTeamId(updateTeamCode.getTeamId());

        if (Objects.isNull(teamCode))
            throw new NotFoundTeamCodeException();

        TeamCodeDtoImpl updateDto = converterToDtoImpl(teamCode);
        updateDto.setTeamName(updateTeamCode.getTeamName());

        repository.updateTeamCode(updateDto);

        return updateDto;
    }

    @Override
    @Transactional
    public Integer deleteTeamCodeById(Integer deleteTeamId) {
        repository.deleteTeamCode(deleteTeamId);
        return deleteTeamId;
    }


    public static TeamCodeDtoImpl converterToDtoImpl(TeamCode teamCode) {
        return  new TeamCodeDtoImpl(teamCode.getTeamId(), teamCode.getTeamName());
    }

    public static TeamCodeDtoImpl converterToDtoImpl(TeamCodeDto teamCodeDto) {
        return new TeamCodeDtoImpl(teamCodeDto.getTeamId(), teamCodeDto.getTeamName());
    }

}
