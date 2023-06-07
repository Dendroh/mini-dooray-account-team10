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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public AccountDetailsDtoImpl getAccountDetailByName(String accountName) {
        AccountDetailsDtoImpl accountTeamBundleDto = detailsRepository.findByName(accountName);

        if (Objects.isNull(accountTeamBundleDto))
            throw new NotFoundAccountDetailsException();

        return accountTeamBundleDto;
    }


    @Override
    public AccountDetailsDtoImpl createAccountDetail(AccountDetailsDtoImpl accountDetailsDto) {
        accountDetailsDto.setImageFileName(accountDetailsDto.getName() + ".png");
        accountDetailsDto.setRegisterDate(LocalDateTime.now());
        accountDetailsDto.setIsDormant(false);
        return converterToDtoImpl(detailsRepository.saveAndFlush(converterToEntity(accountDetailsDto)));
    }


    @Override
    @Transactional
    public AccountDetailsDtoImpl modifyAccountDetail(AccountDetailsDtoImpl accountDetailsDto) {
        AccountDetailsDtoImpl accountDetailImpl = detailsRepository.findByAccountDetailsId(accountDetailsDto.getAccountDetailsId());

        if (Objects.isNull(accountDetailImpl))
            throw new NotFoundAccountDetailsException();

        accountDetailImpl.setName(accountDetailsDto.getName());
        accountDetailImpl.setIsDormant(accountDetailsDto.getIsDormant());
        accountDetailImpl.setImageFileName(accountDetailsDto.getName() + ".png");

        detailsRepository.updateAccountDetails(accountDetailImpl);

        return accountDetailImpl;
    }


    @Override
    @Transactional
    public AccountDetailsDtoImpl deleteAccountDetail(Integer deleteAccountDetailId) {
        detailsRepository.deleteAccountDetails(deleteAccountDetailId);
        return new AccountDetailsDtoImpl(deleteAccountDetailId, null, null, null, null);
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
        accountDetails.setAccountDetailsId(accountDetailsDto.getAccountDetailsId());
        accountDetails.setName(accountDetailsDto.getName());
        accountDetails.setRegisterDate(accountDetailsDto.getRegisterDate());
        accountDetails.setImageFileName(accountDetailsDto.getImageFileName());
        accountDetails.setIsDormant(accountDetailsDto.getIsDormant());

        return accountDetails;
    }


}
