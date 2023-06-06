package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDetailsDto;
import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountDetailsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountDetailsRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("test find & get by condition method return entity and entityDto class")
    void testFindAndGet() {
        AccountDetails testEntity = new AccountDetails();
        Account testEntity2 = new Account();

        testEntity2.setEmail("example16@gmail.com");
        testEntity2.setPassword("$2a$10$guMp1o53D9Q2hq0oWcRpw.wBNZlNVg343kyJ/zyo/6r8eG1Z4Pcz.");

        int accountId = (int) entityManager.persistAndGetId(testEntity2);

        testEntity.setAccountDetailsId(accountId);
        testEntity.setAccount(testEntity2);
        testEntity.setName("name16");
        testEntity.setIsDormant(true);
        testEntity.setRegisterDate(LocalDateTime.now());

        entityManager.persistAndFlush(testEntity);

        assertThat(repository.findByAccountDetailsId(accountId).getIsDormant()).isTrue();
        assertThat(repository.findByName("name16").getAccountDetailsId()).isEqualTo(accountId);
        assertThat(repository.getByAccountDetailsId(accountId).getAccount().getEmail()).isEqualTo("example16@gmail.com");
        assertThat(repository.getByName("name16").getImageFileName()).isNull();
        assertThat(repository.findByIsDormant(true)).hasSize(5);
        assertThat(repository.getByIsDormant(false)).hasSize(16-5);
    }

    @Test
    @DisplayName("test insert accountDetails")
    void testInsertAccountDetails() {
        Account testEntity2 = new Account();

        testEntity2.setEmail("example16@gmail.com");
        testEntity2.setPassword("$2a$10$guMp1o53D9Q2hq0oWcRpw.wBNZlNVg343kyJ/zyo/6r8eG1Z4Pcz.");

        testEntity2 = accountRepository.saveAndFlush(testEntity2);

        AccountDetails details = new AccountDetails();
        details.setAccountDetailsId(testEntity2.getAccountId());
        details.setAccount(testEntity2);
        details.setName("name18");
        details.setIsDormant(false);
        details.setImageFileName(null);
        details.setRegisterDate(LocalDateTime.now());

        assertThat(repository.findByAccountDetailsId(testEntity2.getAccountId())).isNull();

        repository.saveAndFlush(details);

        assertThat(repository.findByAccountDetailsId(testEntity2.getAccountId())).isNotNull();
    }


    @Test
    @DisplayName("test update accountDetails")
    void testUpdateAccountDetails() {

        AccountDetailsDtoImpl details = repository.findByAccountDetailsId(15);
        details.setName("name19");
        details.setIsDormant(true);
        details.setImageFileName("name19.png");
        details.setRegisterDate(LocalDateTime.of(1997, 10, 31, 10, 30));

        assertThat(repository.findByName(details.getName())).isNull();

        repository.updateAccountDetails(details);

        AccountDetails updated = repository.getByAccountDetailsId(15);

        assertThat(updated.getRegisterDate()).isEqualTo(details.getRegisterDate());
        assertThat(updated.getName()).isEqualTo(details.getName());
        assertThat(updated.getIsDormant()).isTrue();
        assertThat(updated.getImageFileName()).isEqualTo(details.getImageFileName());
    }

    @Test
    @DisplayName("test delete accountDetails")
    void testDeleteAccountDetails() {
        AccountDetails accountDetails = repository.getByAccountDetailsId(3);

        assertThat(accountDetails).isNotNull();

        repository.deleteAccountDetails(accountDetails.getAccountDetailsId());

        accountDetails = repository.getByAccountDetailsId(accountDetails.getAccountDetailsId());

        assertThat(accountDetails).isNull();
    }

}