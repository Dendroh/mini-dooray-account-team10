package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.TeamCodeDto;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.domain.TeamCodeUpdateReq;
import com.example.minidoorayaccount.entity.TeamCode;
import com.example.minidoorayaccount.exception.NotFoundTeamCodeException;
import com.example.minidoorayaccount.repository.TeamCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeamCodeServiceTest {

    @MockBean
    private TeamCodeRepository repository;

    @Autowired
    private TeamCodeService service;

    @Test
    void getTeamCodeByTeamId() {
        TeamCodeDto dto = new TeamCodeDtoImpl(1, "testTeam");

        doReturn(dto).when(repository).getTeamCodeByTeamId(1);
        doReturn(null).when(repository).getTeamCodeByTeamId(1000);

        TeamCodeDtoImpl result = service.getTeamCodeByTeamId(1);

        assertThat(result.getTeamName()).isEqualTo(dto.getTeamName());
        Assertions.assertThrows(NotFoundTeamCodeException.class, () -> service.getTeamCodeByTeamId(1000));
    }

    @Test
    void getTeamCodeByTeamName() {
        TeamCodeDto dto = new TeamCodeDtoImpl(1, "testTeam");

        doReturn(dto).when(repository).getTeamCodeByTeamNameLike("testTeam");
        doReturn(null).when(repository).getTeamCodeByTeamNameLike("notFoundTeam");

        TeamCodeDtoImpl result = service.getTeamCodeByTeamName("testTeam");

        assertThat(result.getTeamName()).isEqualTo(dto.getTeamName());
        Assertions.assertThrows(NotFoundTeamCodeException.class, () -> service.getTeamCodeByTeamName("notFoundTeam"));
    }

    @Test
    void createTeamCode() {
        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName("testTeam");
        teamCode.setTeamId(1);

        TeamCodeDtoImpl codeDto = new TeamCodeDtoImpl(teamCode.getTeamId(), teamCode.getTeamName());

        doReturn(teamCode).when(repository).saveAndFlush(any(TeamCode.class));

        TeamCodeDtoImpl result = service.createTeamCode(codeDto);
        assertThat(result.getTeamName()).isEqualTo(teamCode.getTeamName());
    }

    @Test
    void updateTeamCodeById() {
        TeamCodeUpdateReq req = new TeamCodeUpdateReq();
        req.setBeforeTeamName("testTeam");
        req.setAfterTeamName("afterTeam");

        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName("testTeam");
        teamCode.setTeamId(1);

        doReturn(teamCode).when(repository).findByTeamName("testTeam");
        doReturn(null).when(repository).findByTeamName("notFoundTeam");

        TeamCodeDtoImpl dto = service.updateTeamCodeById(req);

        assertThat(dto.getTeamId()).isEqualTo(1);
        assertThat(dto.getTeamName()).isEqualTo("afterTeam");

        req.setBeforeTeamName("notFoundTeam");
        Assertions.assertThrows(NotFoundTeamCodeException.class, () -> service.updateTeamCodeById(req));
        verify(repository, times(1)).updateTeamCode(any(TeamCodeDtoImpl.class));
    }

    @Test
    void deleteTeamCodeById() {
        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName("testTeam");
        teamCode.setTeamId(1);

        doReturn(teamCode).when(repository).findByTeamName("testTeam");
        doReturn(null).when(repository).findByTeamName("notFoundTeam");

        Assertions.assertDoesNotThrow(() -> service.deleteTeamCodeById("testTeam"));
        Assertions.assertThrows(NotFoundTeamCodeException.class, () -> service.deleteTeamCodeById("notFoundTeam"));

        verify(repository, times(1)).deleteTeamCode(anyInt());
    }
}