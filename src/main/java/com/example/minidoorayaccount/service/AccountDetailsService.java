package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;

import java.util.List;

public interface AccountDetailsService {
    List<AccountDetailsDtoImpl> getAccountDetails();

    AccountDetailsDtoImpl getAccountDetailById(Integer accountId);

    AccountDetailsDtoImpl getAccountDetailByName(String accountName);

    AccountDetailsDtoImpl createAccountDetail(AccountDetailsDtoImpl accountDetailsDto);

    AccountDetailsDtoImpl modifyAccountDetail(AccountDetailsDtoImpl accountDetailsDto);

    AccountDetailsDtoImpl deleteAccountDetail(Integer deleteAccountDetailId);
}
