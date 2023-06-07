package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;

import java.util.List;

public interface AccountService {
    AccountDto createAccount();

    AccountDtoImpl getAccountByEmail(String email);

    List<AccountDto> getAccounts();

    AccountDto modifyAccount(AccountDto target);

    void deleteAccount();

}
