package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountTeamCodeBundleRepositoryCustom {
    void updateAccountTeamBundleByTeamId(AccountTeamBundleDtoImpl bundleDto, Integer beforeAccountId);

    void updateAccountTeamBundleByAccountId(AccountTeamBundleDtoImpl bundleDto, Integer beforeTeamId);

    void deleteBundle(Integer deleteTeamCodeId, Integer deleteAccountDetailsId);
}
