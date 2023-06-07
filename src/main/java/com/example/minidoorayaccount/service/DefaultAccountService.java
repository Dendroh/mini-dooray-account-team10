package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {

    private final AccountRepository repository;

    @Override
    public List<AccountDto> getAccounts() {
        return repository.findAllBy();
    }

    @Override
    public AccountDtoImpl getAccountByEmail(String email) {
        return null;
    }

    @Override
    public AccountDto createAccount() {
        return null;
    }


    @Override
    public AccountDto modifyAccount(AccountDto target) {
        return null;
    }

    @Override
    public void deleteAccount() {

    }
}
