package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountDetailsRepositoryCustom {

    void updateAccountDetails(AccountDetailsDtoImpl updateDetails);
    void deleteAccountDetails(Integer detailsId);
}
