package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.domain.AccountDetailsPostReq;
import com.example.minidoorayaccount.domain.AccountDetailsUpdateReq;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.exception.NotFoundAccountDetailsException;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.repository.AccountDetailsRepository;
import com.example.minidoorayaccount.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountDetailsServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountDetailsRepository detailsRepository;

    @Autowired
    private AccountDetailsService service;


    @Test
    void getAccountDetails() {
        doReturn(List.of(new AccountDetailsDtoImpl(1, "test1", "test1.png", false, LocalDateTime.now()),
                         new AccountDetailsDtoImpl(2, "test2", "test2.png", true, LocalDateTime.now()),
                         new AccountDetailsDtoImpl(3, "test3", "test3.png", false, LocalDateTime.now())))
                .when(detailsRepository)
                .findBy();

        List<AccountDetailsDtoImpl> detailsDtoList = service.getAccountDetails();

        assertThat(detailsDtoList).hasSize(3);
        assertThat(detailsDtoList.get(1).getIsDormant()).isTrue();
    }

    @Test
    void getAccountDetailById() {
        doReturn(new AccountDetailsDtoImpl(1, "test1", null, false, LocalDateTime.now()))
                .when(detailsRepository).findByAccountDetailsId(1);

        doReturn(null).when(detailsRepository).findByAccountDetailsId(1000);

        Assertions.assertThrows(NotFoundAccountDetailsException.class, () -> service.getAccountDetailById(1000));
        assertThat(service.getAccountDetailById(1).getImageFileName()).isNull();
        verify(detailsRepository, times(2)).findByAccountDetailsId(anyInt());
    }

    @Test
    void getAccountDetailByName() {
        doReturn(new AccountDetailsDtoImpl(1, "test1", "test1.png", false, LocalDateTime.now()))
                .when(detailsRepository).findByName("test1");

        doReturn(null).when(detailsRepository).findByName("notFoundName");

        Assertions.assertThrows(NotFoundAccountDetailsException.class, () -> service.getAccountDetailByName("notFoundName"));
        assertThat(service.getAccountDetailByName("test1").getAccountDetailsId()).isEqualTo(1);
        verify(detailsRepository, times(2)).findByName(anyString());
    }

    @Test
    void getAccountDetailByEmail() {
        Account searchAccount = new Account();
        searchAccount.setAccountId(1);
        searchAccount.setEmail("testEmail");
        searchAccount.setPassword("testPassword");

        doReturn(searchAccount).when(accountRepository).getByEmail("testEmail");
        doReturn(null).when(accountRepository).getByEmail("notFoundEmail");

        AccountDetailsDtoImpl dto = new AccountDetailsDtoImpl(1, "test", "test.png", false, LocalDateTime.now().plusHours(9));

        doReturn(dto).when(detailsRepository).findByAccountDetailsId(searchAccount.getAccountId());
        doReturn(null).when(detailsRepository).findByAccountDetailsId(1000);

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.getAccountDetailByEmail("notFoundEmail"));

        AccountDetailsDtoImpl result = service.getAccountDetailByEmail("testEmail");

        assertThat(result.getName()).isEqualTo("test");
        assertThat(result.getImageFileName()).isEqualTo("test.png");
        assertThat(result.getIsDormant()).isFalse();

        searchAccount.setAccountId(1000);

        Assertions.assertThrows(NotFoundAccountDetailsException.class, () -> service.getAccountDetailByEmail("testEmail"));
    }

    @Test
    void createAccountDetail() {
        AccountDetailsPostReq postReq = new AccountDetailsPostReq();
        postReq.setAccountEmail("testEmail");
        postReq.setName("testName");

        Account searchAccount = new Account();
        searchAccount.setAccountId(1);
        searchAccount.setEmail("testEmail");
        searchAccount.setPassword("testPassword");

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountDetailsId(1);
        accountDetails.setAccount(searchAccount);
        accountDetails.setIsDormant(false);
        accountDetails.setRegisterDate(LocalDateTime.now().plusHours(9));
        accountDetails.setName("testName");
        accountDetails.setImageFileName("testName.png");

        doReturn(searchAccount).when(accountRepository).getByEmail("testEmail");
        doReturn(searchAccount).when(accountRepository).getByAccountId(1);
        doReturn(accountDetails).when(detailsRepository).saveAndFlush(any(AccountDetails.class));
        doReturn(null).when(accountRepository).getByEmail("notFoundEmail");

        AccountDetailsDtoImpl dto = service.createAccountDetail(postReq);

        assertThat(dto.getName()).isEqualTo(postReq.getName());
        assertThat(dto.getImageFileName()).isEqualTo("testName.png");
        assertThat(dto.getIsDormant()).isFalse();
        assertThat(dto.getRegisterDate()).isAfter(LocalDateTime.now());

        postReq.setAccountEmail("notFoundEmail");

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.createAccountDetail(postReq));
        verify(detailsRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void modifyAccountDetail() {
        AccountDetailsUpdateReq updateReq = new AccountDetailsUpdateReq();
        updateReq.setAccountEmail("testEmail");
        updateReq.setName("testName");
        updateReq.setIsDormant(false);

        Account account = new Account();
        account.setEmail(updateReq.getAccountEmail());
        account.setAccountId(1);
        account.setPassword("testPassword");

        AccountDetails accountDetailsByDto = new AccountDetails();

        accountDetailsByDto.setAccountDetailsId(1);
        accountDetailsByDto.setRegisterDate(LocalDateTime.now().plusHours(9));

        doReturn(account).when(accountRepository).getByEmail(account.getEmail());
        doReturn(null).when(accountRepository).getByEmail("notFoundEmail");
        doReturn(account).when(accountRepository).getByEmail("notFoundDetails");

        doReturn(accountDetailsByDto).when(detailsRepository).getByAccountDetailsId(account.getAccountId());
        doReturn(null).when(detailsRepository).getByAccountDetailsId(1000);

        AccountDetailsDtoImpl updatedDto = service.modifyAccountDetail(updateReq);

        assertThat(updatedDto.getName()).isEqualTo(updateReq.getName());
        assertThat(updatedDto.getAccountDetailsId()).isEqualTo(account.getAccountId());
        assertThat(updatedDto.getImageFileName()).isNull();
        assertThat(updatedDto.getIsDormant()).isEqualTo(updatedDto.getIsDormant());
        assertThat(updatedDto.getRegisterDate().toLocalDate()).isEqualTo(accountDetailsByDto.getRegisterDate().toLocalDate());

        updateReq.setAccountEmail("notFoundEmail");

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.modifyAccountDetail(updateReq));

        updateReq.setAccountEmail("notFoundDetails");
        account.setAccountId(1000);

        Assertions.assertThrows(NotFoundAccountDetailsException.class, () -> service.modifyAccountDetail(updateReq));

    }

    @Test
    void deleteAccountDetail() {
        Account account = new Account();
        account.setEmail("testEmail");
        account.setAccountId(1);
        account.setPassword("testPassword");

        doReturn(account).when(accountRepository).getByEmail(account.getEmail());
        doReturn(null).when(accountRepository).getByEmail("notFoundEmail");

        service.deleteAccountDetail(account.getEmail());

        Assertions.assertThrows(NotFoundAccountException.class, () -> service.deleteAccountDetail("notFoundEmail"));

        verify(detailsRepository, times(1)).deleteById(anyInt());
    }
}