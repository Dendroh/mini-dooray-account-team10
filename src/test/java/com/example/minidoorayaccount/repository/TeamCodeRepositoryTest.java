package com.example.minidoorayaccount.repository;

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
        assertThat(repository.findAllBy()).hasSize(6);
    }

}