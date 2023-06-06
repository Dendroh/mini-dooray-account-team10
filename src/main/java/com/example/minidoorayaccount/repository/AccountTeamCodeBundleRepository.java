package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDto;
import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountTeamCodeBundleRepository extends JpaRepository<AccountTeamBundle, AccountTeamBundle.Pk>, AccountTeamCodeBundleRepositoryCustom {

    List<AccountTeamBundleDto> findAllBy();

    List<AccountTeamBundle> findByAccountDetails_AccountDetailsId(Integer accountId);

    List<AccountTeamBundleDto> getByAccountDetails_Name(String accountName);

    AccountTeamBundleDtoImpl queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(Integer teamId, Integer accountId);

    AccountTeamBundleDtoImpl queryByTeamCode_TeamNameAndAccountDetails_Name(String teamName, String accountName);

    AccountTeamBundle findByAccountDetails_NameAndTeamCode_TeamName(String accountName, String teamName);

}
