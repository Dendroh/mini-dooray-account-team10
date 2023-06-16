package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDto;
import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountTeamCodeBundleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AccountTeamCodeBundleRepository repository;

    private AccountTeamBundle bundle1;

    private AccountTeamBundle bundle2;

    @BeforeEach
    public void setUp() {
        Account account1 = new Account();
        account1.setEmail("email1");
        account1.setPassword("password1");

        Account account2 = new Account();
        account2.setEmail("email2");
        account2.setPassword("password2");

        int accountId1 = (int) entityManager.persistAndGetId(account1);
        int accountId2 = (int) entityManager.persistAndGetId(account2);

        TeamCode teamCode1 = new TeamCode();
        teamCode1.setTeamName("teamm1");

        TeamCode teamCode2 = new TeamCode();
        teamCode2.setTeamName("teamm2");

        entityManager.persist(teamCode1);
        entityManager.persist(teamCode2);

        AccountDetails details1 = new AccountDetails();
        details1.setAccountDetailsId(accountId1);
        details1.setAccount(account1);
        details1.setName("nameeee1");
        details1.setImageFileName(details1.getName() + ".png");
        details1.setRegisterDate(LocalDateTime.now().plusHours(9));
        details1.setIsDormant(false);

        AccountDetails details2 = new AccountDetails();
        details2.setAccountDetailsId(accountId2);
        details2.setAccount(account2);
        details2.setName("nameeee2");
        details2.setImageFileName(details2.getName() + ".png");
        details2.setRegisterDate(LocalDateTime.now().plusHours(9));
        details2.setIsDormant(true);

        entityManager.persistAndFlush(details1);
        entityManager.persistAndFlush(details2);

        bundle1 = new AccountTeamBundle();
        bundle1.setTeamCode(teamCode1);
        bundle1.setAccountDetails(details1);
        bundle1.setPk(new AccountTeamBundle.Pk());
        bundle1.setRegisterDate(LocalDateTime.now().plusHours(9));
        bundle1.getPk().setAccountDetailsId(details1.getAccountDetailsId());
        bundle1.getPk().setTeamId(teamCode1.getTeamId());

        bundle2 = new AccountTeamBundle();
        bundle2.setTeamCode(teamCode2);
        bundle2.setAccountDetails(details2);
        bundle2.setPk(new AccountTeamBundle.Pk());
        bundle2.setRegisterDate(LocalDateTime.now().plusHours(9));
        bundle2.getPk().setAccountDetailsId(details2.getAccountDetailsId());
        bundle2.getPk().setTeamId(teamCode2.getTeamId());

        bundle1 = entityManager.persistAndFlush(bundle1);
        bundle2 = entityManager.persistAndFlush(bundle2);
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findAllBy method return bundleDto List")
    void testFindAllBy() {
        List<AccountTeamBundleDto> accountTeamBundles = repository.findAllBy();

        assertThat(accountTeamBundles).isNotEmpty();

        List<String> accountDetailNames = repository.findAllBy().stream()
                        .map(AccountTeamBundleDto::getAccountDetails)
                                .map(AccountDetails::getName)
                                        .collect(Collectors.toList());

        assertThat(accountDetailNames).contains("nameeee1");
        assertThat(accountDetailNames).contains("nameeee2");
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findByAccountDetails_Name")
    void testFindByAccountDetails_Name() {
        List<AccountTeamBundle> accountTeamBundles1 = repository.findByAccountDetails_Name("nameeee1");
        assertThat(accountTeamBundles1).hasSize(1);

        List<AccountTeamBundle> accountTeamBundles2 = repository.findByAccountDetails_Name("nameeee2");
        assertThat(accountTeamBundles2).hasSize(1);
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's QueryByTeamIdAndAccountId method")
    void testQueryByTeamIdAndAccountId() {
        AccountTeamBundleDtoImpl bundleDto1 = repository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(bundle1.getTeamCode().getTeamId(), bundle1.getAccountDetails().getAccountDetailsId());
        AccountTeamBundleDtoImpl bundleDto2 = repository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(bundle2.getTeamCode().getTeamId(), bundle2.getAccountDetails().getAccountDetailsId());

        assertThat(bundleDto1.getAccountDetails().getName()).isEqualTo(bundle1.getAccountDetails().getName());
        assertThat(bundleDto1.getAccountDetails().getIsDormant()).isFalse();

        assertThat(bundleDto2.getAccountDetails().getName()).isEqualTo(bundle2.getAccountDetails().getName());
        assertThat(bundleDto2.getAccountDetails().getIsDormant()).isTrue();
    }


    @Test
    @DisplayName("test AccountTeamCodeBundle repository's findByAccountNameAndTeamName method")
    void testFindByAccountNameAndTeamName() {
        AccountTeamBundle bundleDto1 = repository.findByAccountDetails_NameAndTeamCode_TeamName(bundle1.getAccountDetails().getName(), bundle1.getTeamCode().getTeamName());

        assertThat(bundleDto1.getTeamCode().getTeamId()).isEqualTo(bundle1.getPk().getTeamId());
        assertThat(bundleDto1.getAccountDetails().getAccountDetailsId()).isEqualTo(bundle1.getPk().getAccountDetailsId());

        AccountTeamBundle bundleDto2 = repository.findByAccountDetails_NameAndTeamCode_TeamName(bundle2.getAccountDetails().getName(), bundle2.getTeamCode().getTeamName());

        assertThat(bundleDto2.getTeamCode().getTeamId()).isEqualTo(bundle2.getPk().getTeamId());
        assertThat(bundleDto2.getAccountDetails().getAccountDetailsId()).isEqualTo(bundle2.getPk().getAccountDetailsId());
    }


    @Test
    @DisplayName("test AccountTeamCodeBundle repository's queryByTeamNameAndAccountName method")
    void testQueryByTeamNameAndAccountName() {
        AccountTeamBundleDtoImpl bundleDto1 = repository.queryByTeamCode_TeamNameAndAccountDetails_Name("teamm1", "nameeee1");
        AccountTeamBundleDtoImpl bundleDto2 = repository.queryByTeamCode_TeamNameAndAccountDetails_Name("teamm2", "nameeee2");

        assertThat(bundleDto1.getTeamCode().getTeamId()).isEqualTo(bundleDto1.getPk().getTeamId());
        assertThat(bundleDto2.getAccountDetails().getAccountDetailsId()).isEqualTo(bundleDto2.getPk().getAccountDetailsId());
    }

    @Test
    @DisplayName("test AccountTeamCodeBundle repository's delete Bundle")
    void testDeleteBundle() {
        AccountTeamBundle before = repository.findByAccountDetails_NameAndTeamCode_TeamName(bundle1.getAccountDetails().getName(), bundle1.getTeamCode().getTeamName());

        assertThat(before).isNotNull();

        repository.deleteByPk_TeamIdAndPk_AccountDetailsId(before.getPk().getTeamId(), before.getPk().getAccountDetailsId());

        AccountTeamBundle after = repository.findByAccountDetails_NameAndTeamCode_TeamName(before.getAccountDetails().getName(), before.getTeamCode().getTeamName());

        assertThat(after).isNull();
    }


}