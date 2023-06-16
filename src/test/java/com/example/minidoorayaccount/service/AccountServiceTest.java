package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.domain.AccountUpdateReq;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class AccountServiceTest {

    @MockBean
    private AccountRepository repository;

    @Autowired
    private AccountService service;

    @Test
    void testCreateAccount() {
        AccountDtoImpl dto = new AccountDtoImpl(null, "test@gmail.com", "$2a$10$SpohekWHF7IctpwT87wY9eaBawJL1YJfXlQMMB.u5xtZ5GiFuhrN6");
        doReturn(DefaultAccountService.converterToEntity(dto)).when(repository).saveAndFlush(any());

        assertThat(service.createAccount(dto)).isEqualTo(dto);
    }

    @Test
    void testGetAccountById() {
        Account account = new Account();
        account.setAccountId(2);
        account.setEmail("test3@gmail.com");
        account.setPassword("$2a$10$DiCK.AG5Oh2i5Zoxd4mtuOR3H4zBNuV5Xs0G33ohPlgST8FFyBlNy");

        doReturn(null).when(repository).queryByAccountId(11111);
        doReturn(DefaultAccountService.converterToDtoImpl(account)).when(repository).queryByAccountId(account.getAccountId());

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.getAccountById(11111));

        assertThat(service.getAccountById(account.getAccountId())).isEqualTo(DefaultAccountService.converterToDtoImpl(account));
    }

    @Test
    void testGetAccountByEmail() {
        AccountDtoImpl account = new AccountDtoImpl(4, "test4@gmail.com",
                "$2a$10$DiCK.AG5Oh2i5Zoxd4mtuOR3H4zBNuV5Xs0G33ohPlgST8FFyBlNy");

        doReturn(null).when(repository).queryByEmail("notFoundEmail");
        doReturn(account).when(repository).queryByEmail("test4@gmail.com");

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.getAccountByEmail("notFoundEmail"));
        assertThat(service.getAccountByEmail(account.getEmail()).getAccountId()).isEqualTo(4);
    }

    @Test
    void testGetAccounts() {
        doReturn(List.of(new AccountDtoImpl(1, "test1@gmail.com", "$2a$10$MSK2t9fqGIwGhInKZu8SC.cpnwGjoKgQysiW2mID.1Veez2h40/5a"),
                         new AccountDtoImpl(2, "test2@gmail.com", "$2a$10$vk/H6B3TO61ZFqz/qVFle.6wE2yNd3JpgZTPT7rNbktmbEGNJvbuq"),
                         new AccountDtoImpl(3, "test3@gmail.com", "$2a$10$lYGBWV664GtX1WNo.Y1gS.Xgf8yguW1l/1wTElHgP15OrvQCHqHe6")))
                .when(repository)
                .findAllBy();

        assertThat(service.getAccounts()).hasSize(3);
    }

    @Test
    void testModifyAccount() {
        AccountDtoImpl account = new AccountDtoImpl(23, "testEmail", "testPassword");

        Account updateAccount = new Account();
        updateAccount.setAccountId(23);
        updateAccount.setEmail("updatedEmail");
        updateAccount.setPassword("updatedPassword");

        doReturn(account).when(repository).queryByEmail("testEmail");
        doReturn(null).when(repository).queryByEmail("notFoundEmail");
        doReturn(updateAccount).when(repository).updateAccount(account);


        AccountUpdateReq updateReq = new AccountUpdateReq();
        updateReq.setBeforeEmail("testEmail");
        updateReq.setAfterEmail("updatedEmail");
        updateReq.setPassword("updatedPassword");

        AccountDtoImpl updatedDto = service.modifyAccount(updateReq);

        assertThat(updatedDto.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(updatedDto.getEmail()).isEqualTo("updatedEmail");
        assertThat(updatedDto.getPassword()).isEqualTo("updatedPassword");

        updateReq.setBeforeEmail("notFoundEmail");

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.modifyAccount(updateReq));
    }

    @Test
    void testDeleteAccount() {
        Account deleteAccount = new Account();
        deleteAccount.setAccountId(100);
        deleteAccount.setEmail("testEmail");
        deleteAccount.setPassword("testPassword");

        doReturn(deleteAccount).when(repository).getByEmail("testEmail");
        doReturn(null).when(repository).getByEmail("notFoundEmail");

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.deleteAccount("notFoundEmail"));
        service.deleteAccount("testEmail");

    }
}