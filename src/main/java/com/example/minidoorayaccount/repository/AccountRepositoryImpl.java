package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.QAccount;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class AccountRepositoryImpl extends QuerydslRepositorySupport implements AccountRepositoryCustom {
    public AccountRepositoryImpl() {
        super(Account.class);
    }

    @Override
    @Transactional
    public Account updateAccount(AccountDtoImpl accountDto) {
        QAccount account = QAccount.account;

        Account updatedAccount = from(account)
                .where(account.accountId.eq(accountDto.getAccountId()))
                        .fetchFirst();

        updatedAccount.setEmail(accountDto.getEmail());
        updatedAccount.setPassword(accountDto.getPassword());

        return updatedAccount;
    }

}
