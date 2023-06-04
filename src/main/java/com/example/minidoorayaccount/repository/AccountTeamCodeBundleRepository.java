package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountTeamBundleDto;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountTeamCodeBundleRepository extends JpaRepository<AccountTeamBundle, AccountTeamBundle.Pk> {

    List<AccountTeamBundleDto> findAllBy();

    List<AccountTeamBundle> findByAccount_AccountId(Integer accountId);


    @Query("select atb from AccountTeamBundle atb join fetch Account a on atb.account.name = a.name " +
            "where atb.account.name = :accountName")
    List<AccountTeamBundleDto> teamNameAndRegisterDatesByAccountName(@Param("accountName") String accountName);

}
