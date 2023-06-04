package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<AccountDto> findAllBy();

    AccountDto findByAccountId(Integer accountId);

    Account getByAccountId(Integer accountId);

    Account getByName(String accountName);

    @Modifying
    @Query("update Account a set " +
            "a.name = :#{#updateAccount.name}, a.password = :#{#updateAccount.password}, a.email = :#{#updateAccount.email}, " +
            "a.imageFileName = :#{#updateAccount.imageFileName}, a.isDormant = :#{#updateAccount.isDormant}, a.registerDate = :#{#updateAccount.registerDate} " +
            "where a.accountId = :#{#updateAccount.accountId}")
    Integer updateAccount(@Param("updateAccount") Account account);

    @Modifying
    @Query("delete from Account a where a.accountId = :deleteAccountId")
    Integer deleteAccountById(@Param("deleteAccountId") Integer accountId);
}
