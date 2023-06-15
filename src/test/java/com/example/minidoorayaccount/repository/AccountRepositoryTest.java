package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountTeamCodeBundleRepository bundleRepository;

    @Autowired
    AccountDetailsRepository detailsRepository;

    private Account account1;

    private Account account2;

    @BeforeEach
    public void setUp() {
        account1 = new Account();
        account1.setEmail("example112@gmail.com");
        account1.setPassword("examplePassword1");

        account1 = entityManager.persistAndFlush(account1);

        account2 = new Account();
        account2.setEmail("example1111@gmail.com");
        account2.setPassword("examplePassword2");

        account2 = entityManager.persistAndFlush(account2);

        AccountDetails details1 = new AccountDetails();
        details1.setAccountDetailsId(account1.getAccountId());
        details1.setAccount(account1);
        details1.setName("nameeee1");
        details1.setImageFileName(details1.getName() + ".png");
        details1.setRegisterDate(LocalDateTime.now().plusHours(9));
        details1.setIsDormant(false);

        entityManager.persistAndFlush(details1);

        TeamCode teamCode1 = new TeamCode();
        teamCode1.setTeamName("teamm1");

        entityManager.persistAndFlush(teamCode1);

        AccountTeamBundle bundle1 = new AccountTeamBundle();
        bundle1.setTeamCode(teamCode1);
        bundle1.setAccountDetails(details1);
        bundle1.setPk(new AccountTeamBundle.Pk());
        bundle1.setRegisterDate(LocalDateTime.now().plusHours(9));
        bundle1.getPk().setAccountDetailsId(details1.getAccountDetailsId());
        bundle1.getPk().setTeamId(teamCode1.getTeamId());

        entityManager.persistAndFlush(bundle1);
    }

    @Test
    @DisplayName("test account repository's findAllBy method return accountDto")
    void testFindAllBy() {
        List<AccountDto> accountList = repository.findAllBy();

        List<String> afterAccountEmailList = accountList.stream()
                        .map(AccountDto::getEmail)
                                .collect(Collectors.toList());

        assertThat(afterAccountEmailList).contains(account1.getEmail());
        assertThat(afterAccountEmailList).contains(account2.getEmail());
    }

    @Test
    @DisplayName("test account repository's getByAccountId method return accountDto")
    void testGetByAccountId() {
        assertThat(repository.getByAccountId(account1.getAccountId()).getEmail()).isEqualTo("example112@gmail.com");
        assertThat(repository.getByEmail(account1.getEmail()).getPassword()).isEqualTo(account1.getPassword());
        assertThat(repository.getByEmail(account2.getEmail()).getPassword()).isEqualTo(account2.getPassword());
        assertThat(repository.getByAccountId(account2.getAccountId()).getEmail()).isEqualTo("example1111@gmail.com");
    }


    @Test
    @DisplayName("test account repository's insert Account")
    void testInsertAccount() {
        Account account = new Account();
        account.setEmail("example52413@gmail.com");
        account.setPassword("examplePassword2");

        Account newAccount = repository.saveAndFlush(account);

        assertThat(newAccount.getEmail()).isEqualTo(account.getEmail());
        assertThat(newAccount.getPassword()).isEqualTo(account.getPassword());
    }

    @Test
    @DisplayName("test account repository's update Account")
    void testUpdateAccount() {
        AccountDto updateAccountDto = repository.findByEmail(account1.getEmail());
        Account updateAccount = repository.getByAccountId(updateAccountDto.getAccountId());
        assertThat(updateAccount.getAccountId()).isEqualTo(account1.getAccountId());

        updateAccount.setEmail("example100@gmail.com");
        updateAccount.setPassword("$2a$10$ritwSwzIdrelD2V4vpgTpOMCAPMV3IYOeKycHaoLcWdUYIqvJQ1HW");

        assertThat(repository.queryByEmail("example100@gmail.com")).isNotNull();

        Account result = repository.getByEmail("example100@gmail.com");

        assertThat(result.getAccountId()).isEqualTo(account1.getAccountId());
        assertThat(result.getEmail()).isEqualTo(updateAccount.getEmail());
        assertThat(result.getPassword()).isEqualTo(updateAccount.getPassword());
    }

    @Test
    @Transactional
    @DisplayName("test account repository's delete Account")
    void testDeleteAccount() {
        Account deleteAccount = repository.getByAccountId(account1.getAccountId());
        List<AccountTeamBundle> deletedBundle = bundleRepository.findByAccountDetails_AccountDetailsId(account1.getAccountId());
        AccountDetails deleteDetails = detailsRepository.getByAccountDetailsId(account1.getAccountId());

        assertThat(deleteAccount).isNotNull();
        assertThat(deletedBundle).isNotEmpty();
        assertThat(deleteDetails).isNotNull();

        repository.deleteById(account1.getAccountId());

        entityManager.clear();

        deleteAccount = repository.getByAccountId(account1.getAccountId());
        deletedBundle = bundleRepository.findByAccountDetails_AccountDetailsId(account1.getAccountId());
        deleteDetails = detailsRepository.getByAccountDetailsId(account1.getAccountId());

        assertThat(deletedBundle).isEmpty();
        assertThat(deleteAccount).isNull();
        assertThat(deleteDetails).isNull();
    }

}