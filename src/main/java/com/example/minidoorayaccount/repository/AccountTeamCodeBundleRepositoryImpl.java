package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class AccountTeamCodeBundleRepositoryImpl extends QuerydslRepositorySupport implements AccountTeamCodeBundleRepositoryCustom {

    public AccountTeamCodeBundleRepositoryImpl() {
        super(AccountTeamBundle.class);
    }

    @Override
    public void updateAccountTeamBundleByTeamId(AccountTeamBundleDtoImpl bundleDto, Integer beforeAccountId) {
        QAccountDetails accountDetails = QAccountDetails.accountDetails;
        QAccountTeamBundle accountTeamBundle = QAccountTeamBundle.accountTeamBundle;


        AccountDetails accountDetailsByDto = from(accountDetails)
                .where(accountDetails.accountDetailsId.eq(bundleDto.getPk().getAccountDetailsId()))
                .fetchFirst();


        update(accountTeamBundle)
                .set(accountTeamBundle.pk.accountDetailsId, bundleDto.getPk().getAccountDetailsId())
                .set(accountTeamBundle.registerDate, bundleDto.getRegisterDate())
                .set(accountTeamBundle.accountDetails, accountDetailsByDto)
                .where(accountTeamBundle.pk.teamId.eq(bundleDto.getPk().getTeamId()).and(accountTeamBundle.pk.accountDetailsId.eq(beforeAccountId)))
                .execute();
    }

    @Override
    public void updateAccountTeamBundleByAccountId(AccountTeamBundleDtoImpl bundleDto, Integer beforeTeamId) {
        QTeamCode teamCode = QTeamCode.teamCode;
        QAccountTeamBundle accountTeamBundle = QAccountTeamBundle.accountTeamBundle;

        TeamCode teamCodeByDto = from(teamCode)
                .where(teamCode.teamId.eq(bundleDto.getPk().getTeamId()))
                .fetchFirst();

        update(accountTeamBundle)
                .set(accountTeamBundle.pk.teamId, bundleDto.getPk().getTeamId())
                .set(accountTeamBundle.registerDate, bundleDto.getRegisterDate())
                .set(accountTeamBundle.teamCode, teamCodeByDto)
                .where(accountTeamBundle.pk.accountDetailsId.eq(bundleDto.getPk().getAccountDetailsId())
                        .and(accountTeamBundle.pk.teamId.eq(beforeTeamId)))
                .execute();
    }

    @Override
    public void deleteBundle(Integer deleteTeamCodeId, Integer deleteAccountDetailsId) {

        QAccountTeamBundle accountTeamBundle = QAccountTeamBundle.accountTeamBundle;

        delete(accountTeamBundle)
                .where(accountTeamBundle.pk.accountDetailsId.eq(deleteAccountDetailsId)
                        .and(accountTeamBundle.pk.teamId.eq(deleteTeamCodeId)))
                .execute();
    }
}
