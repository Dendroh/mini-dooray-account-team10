package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.domain.AccountDetailsPostReq;
import com.example.minidoorayaccount.domain.AccountDetailsUpdateReq;

import java.util.List;

public interface AccountDetailsService {
    List<AccountDetailsDtoImpl> getAccountDetails();

    AccountDetailsDtoImpl getAccountDetailById(Integer accountId);

    AccountDetailsDtoImpl getAccountDetailByName(String accountName);

    AccountDetailsDtoImpl getAccountDetailByEmail(String email);

    AccountDetailsDtoImpl createAccountDetail(AccountDetailsPostReq accountDetailsDto);

    AccountDetailsDtoImpl modifyAccountDetail(AccountDetailsUpdateReq accountDetailsDto);

    void deleteAccountDetail(String deleteAccountEmail);
}
