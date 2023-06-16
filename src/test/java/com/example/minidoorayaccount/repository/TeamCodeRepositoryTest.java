package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.TeamCodeDto;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.TeamCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamCodeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TeamCodeRepository repository;

    @Test
    @DisplayName("test teamCode repository's getTeamCodes method return teamCodeDto")
    void testGetTeamCodes() {
        assertThat(repository.findAllBy()).isNotEmpty();
    }

    @Test
    @DisplayName("test teamCode repository's getTeamCodeByTeamId method return teamCodeDto")
    void testGetTeamCodeByTeamId() {
        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName("NHN-test");

        int teamCodeId = (int) entityManager.persistAndGetId(teamCode);

        assertThat(repository.getTeamCodeByTeamId(teamCodeId).getTeamName()).isEqualTo(teamCode.getTeamName());
    }


    @Test
    @DisplayName("test teamCode repository's getTeamCodeByTeamNameLike method return teamCodeDto")
    void testGetTeamCodeByTeamNameLike() {
        TeamCode teamCode = new TeamCode();
        teamCode.setTeamName("NHN-test");

        entityManager.persistAndFlush(teamCode);

        TeamCodeDto result = repository.getTeamCodeByTeamNameLike("NHN-test");

        assertThat(result.getTeamName()).isEqualTo(teamCode.getTeamName());
    }


    @Test
    @DisplayName("test teamCode repository's update teamCode")
    void testUpdateTeamCode() {
        TeamCodeDtoImpl updateTeamCode = repository.queryByTeamId(4);

        assertThat(updateTeamCode).isNotNull();
        assertThat(updateTeamCode.getTeamName()).isEqualTo("Team4");

        updateTeamCode.setTeamName("team1000");

        assertThat(repository.queryByTeamName("team1000")).isNull();

        repository.updateTeamCode(updateTeamCode);

        assertThat(updateTeamCode.getTeamName()).isEqualTo("team1000");
    }

    @Test
    @DisplayName("test teamCode repository's delete teamCode")
    void testDeleteTeamCode() {
        TeamCode deleteTeamCode = repository.findByTeamName("Team2");
         assertThat(deleteTeamCode).isNotNull();
         assertThat(deleteTeamCode.getTeamId()).isEqualTo(2);

         repository.deleteTeamCode(deleteTeamCode.getTeamId());

         deleteTeamCode = repository.findByTeamName("Team2");

         assertThat(deleteTeamCode).isNull();

        Assertions.assertDoesNotThrow(() -> {
            repository.deleteTeamCode(1000);
        });
    }

}