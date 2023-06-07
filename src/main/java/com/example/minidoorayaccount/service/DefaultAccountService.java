package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Objects;

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
        AccountDtoImpl accountDto = repository.queryByEmail(email);

        if (Objects.isNull(accountDto))
            throw new NotFoundAccountException();

        return accountDto;
    }

    @Override
    public AccountDtoImpl getAccountById(Integer accountId) {
        AccountDtoImpl accountDto = repository.queryByAccountId(accountId);

        if (Objects.isNull(accountDto))
            throw new NotFoundAccountException();

        return accountDto;
    }

    @Override
    public AccountDtoImpl createAccount(AccountDtoImpl accountDto) {
        return converterToDtoImpl(repository.saveAndFlush(converterToEntity(accountDto)));
    }

    @Override
    @Transactional
    public AccountDtoImpl modifyAccount(AccountDtoImpl accountDto) {
        if (Objects.isNull(repository.getByAccountId(accountDto.getAccountId())))
            throw new NotFoundAccountException();

        repository.updateAccount(accountDto);
        return repository.queryByAccountId(accountDto.getAccountId());
    }

    @Override
    @Transactional
    public Integer deleteAccount(Integer deleteId) {
        repository.deleteAccountById(deleteId);
        return deleteId;
    }

    public static Account converterToEntity(AccountDtoImpl accountDto) {
        Account account = new Account();
        account.setAccountId(account.getAccountId());
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());

        return account;
    }

    public static AccountDtoImpl converterToDtoImpl(Account account) {
        return new AccountDtoImpl(account.getAccountId(), account.getEmail(), account.getPassword());
    }
}
