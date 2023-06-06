package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.QAccount;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class AccountRepositoryImpl extends QuerydslRepositorySupport implements AccountRepositoryCustom {
    public AccountRepositoryImpl() {
        super(Account.class);
    }

    @Override
    public void updateAccount(AccountDtoImpl accountDto) {

        QAccount account = QAccount.account;

        update(account)
                .set(account.email, accountDto.getEmail())
                .set(account.password, accountDto.getPassword())
                .where(account.accountId.eq(accountDto.getAccountId()))
                .execute();
    }

    @Override
    public void deleteAccountById(Integer accountId) {

        QAccount account = QAccount.account;

        delete(account)
                .where(account.accountId.eq(accountId))
                .execute();
    }
}
