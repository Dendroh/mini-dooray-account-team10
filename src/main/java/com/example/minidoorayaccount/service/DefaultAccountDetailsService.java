package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.domain.AccountDetailsPostReq;
import com.example.minidoorayaccount.domain.AccountDetailsUpdateReq;
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
    public AccountDetailsDtoImpl getAccountDetailByEmail(String email) {
        Account account = accountRepository.getByEmail(email);

        if (Objects.isNull(account))
            throw new NotFoundAccountException();

        AccountDetailsDtoImpl accountDetailsDto = detailsRepository.findByAccountDetailsId(account.getAccountId());

        if (Objects.isNull(accountDetailsDto))
            throw new NotFoundAccountDetailsException();

        return accountDetailsDto;
    }


    @Override
    public AccountDetailsDtoImpl createAccountDetail(AccountDetailsPostReq accountDetailsDto) {
        Account account = accountRepository.getByEmail(accountDetailsDto.getAccountEmail());

        if (Objects.isNull(account))
            throw new NotFoundAccountException();

        AccountDetailsDtoImpl detailsDto = new AccountDetailsDtoImpl(account.getAccountId(),
                accountDetailsDto.getName(), accountDetailsDto.getName() + ".png",
                false, LocalDateTime.now().plusHours(9));

        return converterToDtoImpl(detailsRepository.saveAndFlush(converterToEntity(detailsDto)));
    }


    @Override
    @Transactional
    public AccountDetailsDtoImpl modifyAccountDetail(AccountDetailsUpdateReq accountDetailsDto) {
        Account account = accountRepository.getByEmail(accountDetailsDto.getAccountEmail());

        if (Objects.isNull(account))
            throw new NotFoundAccountException();

        AccountDetails accountDetail= detailsRepository.getByAccountDetailsId(account.getAccountId());

        if (Objects.isNull(accountDetail))
            throw new NotFoundAccountDetailsException();

        accountDetail.setName(accountDetailsDto.getName());
        accountDetail.setIsDormant(accountDetailsDto.getIsDormant());
        accountDetail.setImageFileName(accountDetailsDto.getName() + ".png");


        return converterToDtoImpl(accountDetail);
    }


    @Override
    @Transactional
    public void deleteAccountDetail(String deleteAccountEmail) {
        Account account = accountRepository.getByEmail(deleteAccountEmail);

        if (Objects.isNull(account))
            throw new NotFoundAccountException();

        detailsRepository.deleteById(account.getAccountId());
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
