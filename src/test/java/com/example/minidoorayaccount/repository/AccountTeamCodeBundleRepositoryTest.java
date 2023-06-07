package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDto;
import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
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

        assertThat(accountTeamBundles).hasSize(15);

        assertThat(accountTeamBundles.get(0).getTeamCode().getTeamId()).isEqualTo(1);
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findByAccountDetails_Name")
    void testFindByAccountDetails_Name() {
        List<AccountTeamBundle> accountTeamBundles = repository.findByAccountDetails_Name("name10");

        assertThat(accountTeamBundles).hasSize(2);
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's registerDatesByAccountName method")
    void testRegisterDatesByAccountName() {
        List<AccountTeamBundleDto> registerDateList = repository.getByAccountDetails_Name("name11");

        for (AccountTeamBundleDto bundleDto : registerDateList) {
            assertThat(bundleDto.getRegisterDate()).isBetween("2023-06-03T02:10:09", "2023-06-03T02:10:11");
            assertThat(bundleDto.getTeamCode()).isNotNull();
        }
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's QueryByTeamIdAndAccountId method")
    void testQueryByTeamIdAndAccountId() {
        AccountTeamBundleDtoImpl bundleDto = repository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(6, 10);

        assertThat(bundleDto.getTeamCode().getTeamId()).isEqualTo(6);
        assertThat(bundleDto.getAccountDetails().getAccountDetailsId()).isEqualTo(10);
    }


    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findByAccountNameAndTeamName method")
    void testFindByAccountNameAndTeamName() {
        AccountTeamBundle bundleDto = repository.findByAccountDetails_NameAndTeamCode_TeamName("name14", "Team1");

        assertThat(bundleDto.getTeamCode().getTeamId()).isEqualTo(1);
        assertThat(bundleDto.getAccountDetails().getAccountDetailsId()).isEqualTo(14);
    }


    @Test
    @DisplayName("test AccountTeamCodeBundle repository's queryByTeamNameAndAccountName method")
    void testQueryByTeamNameAndAccountName() {
        AccountTeamBundleDtoImpl bundleDto = repository.queryByTeamCode_TeamNameAndAccountDetails_Name("Team2", "name4");

        assertThat(bundleDto.getTeamCode().getTeamId()).isEqualTo(2);
        assertThat(bundleDto.getAccountDetails().getAccountDetailsId()).isEqualTo(4);

    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's updateAccountTeamBundleByAccountId method")
    void testUpdateAccountTeamBundleByAccountId() {
        AccountTeamBundleDtoImpl updateDto = repository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(3, 5);
        AccountTeamBundle before = repository.findByAccountDetails_NameAndTeamCode_TeamName("name5", "Team3");

        updateDto.getPk().setTeamId(5);
        updateDto.setRegisterDate(LocalDateTime.now());

        assertThat(before.getPk().getTeamId()).isEqualTo(3);

        repository.updateAccountTeamBundleByAccountId(updateDto, 3);

        AccountTeamBundle after = repository.findByAccountDetails_NameAndTeamCode_TeamName("name5", "Team5");

        assertThat(after.getPk().getTeamId()).isEqualTo(5);
        assertThat(after.getPk().getAccountDetailsId()).isEqualTo(5);
        assertThat(after.getRegisterDate()).isNotEqualTo(before.getRegisterDate());
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's delete Bundle")
    void testDeleteBundle() {
        AccountTeamBundle before = repository.findByAccountDetails_NameAndTeamCode_TeamName("name5", "Team3");

        assertThat(before).isNotNull();

        repository.deleteBundle(3, 5);

        AccountTeamBundle after = repository.findByAccountDetails_NameAndTeamCode_TeamName("name5", "Team3");

        assertThat(after).isNull();
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's updateAccountTeamBundleByTeamId method")
    void testUpdateAccountTeamBundleByTeamId() {
        AccountTeamBundleDtoImpl updateDto = repository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(2, 4);

        updateDto.getPk().setAccountDetailsId(8);
        updateDto.setRegisterDate(LocalDateTime.now());

        repository.updateAccountTeamBundleByTeamId(updateDto, 4);

        AccountTeamBundle after = repository.findByAccountDetails_NameAndTeamCode_TeamName("name4", "Team2");
        assertThat(after).isNull();

        after = repository.findByAccountDetails_NameAndTeamCode_TeamName("name8", "Team2");

        assertThat(after.getPk().getTeamId()).isEqualTo(2);
        assertThat(after.getPk().getAccountDetailsId()).isEqualTo(8);
    }

}