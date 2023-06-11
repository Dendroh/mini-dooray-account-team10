package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.exception.NotFoundAccountDetailsException;
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
    void createAccountDetail() {

    }

    @Test
    void modifyAccountDetail() {
    }

    @Test
    void deleteAccountDetail() {
    }
}