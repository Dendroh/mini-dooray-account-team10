package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.domain.AccountUpdateReq;

import java.util.List;

public interface AccountService {
    AccountDtoImpl createAccount(AccountDtoImpl accountDto);

    AccountDtoImpl getAccountById(Integer accountId);

    AccountDtoImpl getAccountByEmail(String email);

    List<AccountDto> getAccounts();

    AccountDtoImpl modifyAccount(AccountUpdateReq accountDto);

    void deleteAccount(String deleteEmail);

}
