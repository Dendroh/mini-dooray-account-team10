package com.example.minidoorayaccount.repository;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer>, AccountDetailsRepositoryCustom {

    AccountDetailsDtoImpl findByAccountDetailsId(Integer accountDetailsId);

    AccountDetails getByAccountDetailsId(Integer accountDetailsId);

    AccountDetailsDtoImpl findByName(String name);

    AccountDetails getByName(String name);

    AccountDetailsDtoImpl findByAccount_Email(String accountEmail);

    AccountDetails getByAccount_Email(String accountEmail);

    List<AccountDetailsDtoImpl> findByIsDormant(Boolean isDormant);

    List<AccountDetailsDtoImpl> findBy();

    List<AccountDetails> getByIsDormant(Boolean isDormant);

    @Override
    <S extends AccountDetails> S saveAndFlush(S entity);
}
