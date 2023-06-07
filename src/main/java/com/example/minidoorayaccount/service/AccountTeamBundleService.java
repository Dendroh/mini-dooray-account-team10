package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.domain.AccountTeamBundlePostRequest;
import com.example.minidoorayaccount.domain.AccountTeamBundleUpdateRequest;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;

import java.util.List;

public interface AccountTeamBundleService {
    List<TeamCodeDtoImpl> getTeamNamesByAccountId(Integer accountId);

    List<TeamCodeDtoImpl> getTeamNamesByAccountName(String accountName);

    AccountTeamBundleDtoImpl createAccountTeamBundle(AccountTeamBundlePostRequest accountTeamBundleDto);

    AccountTeamBundleDtoImpl updateAccountTeamBundle(AccountTeamBundleUpdateRequest accountTeamBundleDto);

    AccountTeamBundlePostRequest deleteAccountTeamBundle(Integer deleteTeamId, Integer deleteAccountId);

}
