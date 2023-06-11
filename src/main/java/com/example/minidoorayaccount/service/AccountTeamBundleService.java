package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.*;

import java.util.List;

public interface AccountTeamBundleService {
    List<TeamCodeDtoImpl> getTeamNamesByAccountId(Integer accountId);

    List<TeamCodeDtoImpl> getTeamNamesByAccountName(String accountName);

    List<TeamCodeDtoImpl> getTeamNamesByAccountEmail(String accountEmail);

    AccountTeamBundleRespDto createAccountTeamBundle(AccountTeamCodeBundlePostReq accountTeamBundleDto);

    AccountTeamBundleRespDto updateAccountTeamBundle(AccountTeamBundleUpdateReq accountTeamBundleDto);

    void deleteAccountTeamBundle(String deleteTeamName, String deleteAccountEmail);

}
