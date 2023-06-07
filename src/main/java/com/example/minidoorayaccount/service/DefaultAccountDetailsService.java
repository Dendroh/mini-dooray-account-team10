package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.exception.NotFoundAccountDetailsException;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.repository.AccountDetailsRepository;
import com.example.minidoorayaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultAccountDetailsService implements AccountDetailsService {

    private final AccountDetailsRepository detailsRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<AccountDetailsDtoImpl> getAccountDetails() {
        return detailsRepository.findBy();
    }

    @Override
    public AccountDetailsDtoImpl getAccountDetailById(Integer accountId) {
        AccountDetailsDtoImpl accountTeamBundleDto = detailsRepository.findByAccountDetailsId(accountId);

        if (Objects.isNull(accountTeamBundleDto))
            throw new NotFoundAccountDetailsException();

        return accountTeamBundleDto;
    }

    @Override
    public AccountDetailsDtoImpl createAccountDetail(AccountDetailsDtoImpl accountDetailsDto) {
        return converterToDtoImpl(detailsRepository.saveAndFlush(converterToEntity(accountDetailsDto)));
    }


    @Override
    public AccountDetailsDtoImpl modifyAccountDetail(AccountDetailsDtoImpl accountDetailsDto) {
        AccountDetailsDtoImpl accountTeamBundleDto = detailsRepository.findByAccountDetailsId(accountDetailsDto.getAccountDetailsId());

        if (Objects.isNull(accountTeamBundleDto))
            throw new NotFoundAccountDetailsException();

        detailsRepository.updateAccountDetails(accountDetailsDto);

        return accountDetailsDto;
    }



    public AccountDetailsDtoImpl converterToDtoImpl(AccountDetails accountDetail) {
        return new AccountDetailsDtoImpl(accountDetail.getAccountDetailsId(),
                accountDetail.getName(), accountDetail.getImageFileName(), accountDetail.getIsDormant(), accountDetail.getRegisterDate());
    }

    public AccountDetails converterToEntity(AccountDetailsDtoImpl accountDetailsDto) {
        AccountDetails accountDetails = new AccountDetails();
        Account account = accountRepository.getByAccountId(accountDetailsDto.getAccountDetailsId());

        if (Objects.isNull(account))
            throw new NotFoundAccountException();

        accountDetails.setAccount(account);
        accountDetails.setAccountDetailsId(accountDetails.getAccountDetailsId());
        accountDetails.setRegisterDate(accountDetailsDto.getRegisterDate());
        accountDetails.setImageFileName(accountDetailsDto.getImageFileName());
        accountDetails.setName(accountDetails.getName());

        return accountDetails;
    }


}
