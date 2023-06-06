package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount();

    AccountDto getAccount(Integer id);

    List<AccountDto> getAccounts();

    AccountDto modifyAccount(AccountDto target);

    void deleteAccount();

}
