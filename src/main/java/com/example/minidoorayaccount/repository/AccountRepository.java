package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer>, AccountRepositoryCustom {
    List<AccountDto> findAllBy();

    AccountDto findByAccountId(Integer accountId);

    AccountDto findByEmail(String email);

    Account getByAccountId(Integer accountId);

    Account getByEmail(String email);

    AccountDtoImpl queryByAccountId(Integer accountId);

    AccountDtoImpl queryByEmail(String email);

    @Override
    <S extends Account> S saveAndFlush(S entity);
}
