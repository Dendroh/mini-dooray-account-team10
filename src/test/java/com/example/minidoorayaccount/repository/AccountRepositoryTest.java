package com.example.minidoorayaccount.repository;

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
        account.setName("name16");
        account.setPassword("$2a$10$SshpZqNlZ1uDXJeeq4dVROKraZnGcXMcyxrlB9vUH4wpmZ.dORbr.");
        account.setEmail("example16@gmail.com");
        account.setImageFileName("name16.png");
        account.setIsDormant(false);
        account.setRegisterDate(LocalDateTime.now());

        int id = (int) entityManager.persistAndGetId(account);

        assertThat(repository.getByAccountId(id)).isNotNull();
        assertThat(repository.getByAccountId(id).getName()).isEqualTo("name16");
    }


    @Test
    @DisplayName("test account repository's insert Account")
    void testInsertAccount() {
        Account account = new Account();
        account.setName("name16");
        account.setPassword("$2a$10$SshpZqNlZ1uDXJeeq4dVROKraZnGcXMcyxrlB9vUH4wpmZ.dORbr.");
        account.setEmail("example16@gmail.com");
        account.setImageFileName("name16.png");
        account.setIsDormant(false);
        account.setRegisterDate(LocalDateTime.now());

        Account newAccount = repository.saveAndFlush(account);

        assertThat(newAccount.getName()).isEqualTo("name16");
    }

    @Test
    @DisplayName("test account repository's update Account")
    void testUpdateAccount() {
        Account updateAccount = repository.getByName("name2");
        assertThat(updateAccount.getAccountId()).isEqualTo(2);

        updateAccount.setName("name100");
        updateAccount.setPassword("$2a$10$ritwSwzIdrelD2V4vpgTpOMCAPMV3IYOeKycHaoLcWdUYIqvJQ1HW");
        updateAccount.setImageFileName("name100.png");
        updateAccount.setIsDormant(true);
        updateAccount.setEmail("example100@gmail.com");

        updateAccount = repository.getByName("name100");

        assertThat(updateAccount.getAccountId()).isEqualTo(2);
        assertThat(updateAccount.getPassword()).isEqualTo("$2a$10$ritwSwzIdrelD2V4vpgTpOMCAPMV3IYOeKycHaoLcWdUYIqvJQ1HW");

    }

    @Test
    @DisplayName("test account repository's delete Account")
    void testDeleteAccount() {
        Account deleteAccount = repository.getByAccountId(4);
        List<AccountTeamBundle> deletedBundle = bundleRepository.findByAccount_AccountId(4);

        assertThat(deleteAccount).isNotNull();
        assertThat(deletedBundle).hasSize(1);

        repository.deleteAccountById(deleteAccount.getAccountId());

        deleteAccount = repository.getByAccountId(deleteAccount.getAccountId());
        deletedBundle = bundleRepository.findByAccount_AccountId(4);

        assertThat(deleteAccount).isNull();
        assertThat(deletedBundle).isEmpty();
    }




}