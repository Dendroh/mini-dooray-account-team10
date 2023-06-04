package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountTeamCodeBundleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AccountTeamCodeBundleRepository repository;

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findAllBy method return bundleDto List")
    void testFindAllBy() {
        List<AccountTeamBundleDto> accountTeamBundles = repository.findAllBy();

        assertThat(accountTeamBundles).hasSize(17);

        assertThat(accountTeamBundles.get(0).getTeamCode().getTeamId()).isEqualTo(1);
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's registerDatesByAccountName method")
    void testRegisterDatesByAccountName() {
        List<AccountTeamBundleDto> registerDateList = repository.teamNameAndRegisterDatesByAccountName("name11");

        for (AccountTeamBundleDto bundleDto : registerDateList) {
            assertThat(bundleDto.getRegisterDate()).isBetween("2023-06-03T02:10:09", "2023-06-03T02:10:11");
            assertThat(bundleDto.getTeamCode()).isNotNull();
        }
    }


}