package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Override
    <S extends Account> S saveAndFlush(S entity);


}
