package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
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
class AccountRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountTeamCodeBundleRepository bundleRepository;


    @Test
    @DisplayName("test account repository's findAllBy method return accountDto")
    void testFindAllBy() {
        assertThat(repository.findAllBy()).hasSize(15);
    }

    @Test
    @DisplayName("test account repository's getByAccountId method return accountDto")
    void testGetByAccountId() {
        Account account = new Account();
        account.setPassword("$2a$10$SshpZqNlZ1uDXJeeq4dVROKraZnGcXMcyxrlB9vUH4wpmZ.dORbr.");
        account.setEmail("example16@gmail.com");

        int id = (int) entityManager.persistAndGetId(account);

        assertThat(repository.getByAccountId(id)).isNotNull();
        assertThat(repository.getByAccountId(id).getEmail()).isEqualTo("example16@gmail.com");
        assertThat(repository.findByAccountId(id)).isNotNull();

    }


    @Test
    @DisplayName("test account repository's insert Account")
    void testInsertAccount() {
        Account account = new Account();
        account.setPassword("$2a$10$SshpZqNlZ1uDXJeeq4dVROKraZnGcXMcyxrlB9vUH4wpmZ.dORbr.");
        account.setEmail("example16@gmail.com");

        Account newAccount = repository.saveAndFlush(account);

        assertThat(newAccount.getEmail()).isEqualTo("example16@gmail.com");
    }

    @Test
    @DisplayName("test account repository's update Account")
    void testUpdateAccount() {
        AccountDto updateAccountDto = repository.findByEmail("example2@gmail.com");
        AccountDtoImpl updateAccount = repository.queryByAccountId(updateAccountDto.getAccountId());
        assertThat(updateAccount.getAccountId()).isEqualTo(2);

        updateAccount.setPassword("$2a$10$ritwSwzIdrelD2V4vpgTpOMCAPMV3IYOeKycHaoLcWdUYIqvJQ1HW");
        updateAccount.setEmail("example100@gmail.com");

        assertThat(repository.queryByEmail("example100@gmail.com")).isNull();

        repository.updateAccount(updateAccount);

        Account result = repository.getByEmail("example100@gmail.com");

        assertThat(result.getAccountId()).isEqualTo(2);
        assertThat(result.getPassword()).isEqualTo("$2a$10$ritwSwzIdrelD2V4vpgTpOMCAPMV3IYOeKycHaoLcWdUYIqvJQ1HW");
    }

    @Test
    @DisplayName("test account repository's delete Account")
    void testDeleteAccount() {
        Account deleteAccount = repository.getByAccountId(4);
        List<AccountTeamBundle> deletedBundle = bundleRepository.findByAccountDetails_AccountDetailsId(4);

        assertThat(deleteAccount).isNotNull();
        assertThat(deletedBundle).hasSize(1);

        repository.deleteAccountById(deleteAccount.getAccountId());

        deleteAccount = repository.getByAccountId(deleteAccount.getAccountId());
        deletedBundle = bundleRepository.findByAccountDetails_AccountDetailsId(4);

        assertThat(deleteAccount).isNull();
        assertThat(deletedBundle).isEmpty();
    }


}