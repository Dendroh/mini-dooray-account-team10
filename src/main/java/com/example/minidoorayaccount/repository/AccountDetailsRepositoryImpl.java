package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.QAccountDetails;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class AccountDetailsRepositoryImpl extends QuerydslRepositorySupport implements AccountDetailsRepositoryCustom {

    public AccountDetailsRepositoryImpl() {
        super(AccountDetails.class);
    }

    @Override
    public void updateAccountDetails(AccountDetailsDtoImpl updateDetails) {

        QAccountDetails accountDetails = QAccountDetails.accountDetails;

        update(accountDetails)
                .set(accountDetails.name, updateDetails.getName())
                .set(accountDetails.isDormant, updateDetails.getIsDormant())
                .set(accountDetails.registerDate, updateDetails.getRegisterDate())
                .set(accountDetails.imageFileName, updateDetails.getImageFileName())
                .where(accountDetails.accountDetailsId.eq(updateDetails.getAccountDetailsId()))
                .execute();
    }

    @Override
    public void deleteAccountDetails(Integer detailsId) {
        QAccountDetails accountDetails = QAccountDetails.accountDetails;

        delete(accountDetails)
                .where(accountDetails.accountDetailsId.eq(detailsId))
                .execute();
    }
}
