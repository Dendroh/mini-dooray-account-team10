package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDtoImpl;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountRepositoryCustom {
    void updateAccount(AccountDtoImpl account);

    void deleteAccountById(Integer accountId);

}
