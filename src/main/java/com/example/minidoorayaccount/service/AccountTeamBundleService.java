package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.*;

import java.util.List;

public interface AccountTeamBundleService {
    List<TeamCodeDtoImpl> getTeamNamesByAccountId(Integer accountId);

    List<TeamCodeDtoImpl> getTeamNamesByAccountName(String accountName);

    AccountTeamBundleReqDto createAccountTeamBundle(AccountTeamBundleReqDto accountTeamBundleDto);

    AccountTeamBundleReqDto updateAccountTeamBundle(AccountTeamBundleUpdateRequest accountTeamBundleDto);

    AccountTeamBundleReqDto deleteAccountTeamBundle(Integer deleteTeamId, Integer deleteAccountId);

}
