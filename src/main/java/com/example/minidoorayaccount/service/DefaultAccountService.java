package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.domain.AccountUpdateReq;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AccountDtoImpl modifyAccount(AccountUpdateReq accountDto) {
        AccountDtoImpl accountDtoById = repository.queryByEmail(accountDto.getBeforeEmail());

        if (Objects.isNull(accountDtoById))
            throw new NotFoundAccountException();

        accountDtoById.setEmail(accountDto.getAfterEmail());
        accountDtoById.setPassword(accountDto.getPassword());

        return accountDtoById;
    }

    @Override
    @Transactional
    public void deleteAccount(String deleteEmail) {
        Account deleteAccount = repository.getByEmail(deleteEmail);

        if (Objects.isNull(deleteAccount))
            throw new NotFoundAccountException();

        repository.deleteById(deleteAccount.getAccountId());
    }

    public static Account converterToEntity(AccountDtoImpl accountDto) {
        Account account = new Account();
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());

        return account;
    }

    public static AccountDtoImpl converterToDtoImpl(Account account) {
        return new AccountDtoImpl(account.getAccountId(), account.getEmail(), account.getPassword());
    }
}
