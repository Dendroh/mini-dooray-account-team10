package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<AccountDto> findAllBy();
}
