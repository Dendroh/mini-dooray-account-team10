package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountRepositoryCustom {
    Account updateAccount(AccountDtoImpl updateReq);

}
