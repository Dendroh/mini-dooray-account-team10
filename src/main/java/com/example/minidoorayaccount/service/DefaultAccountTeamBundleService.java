package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.AccountTeamBundleDtoImpl;
import com.example.minidoorayaccount.domain.AccountTeamBundlePostRequest;
import com.example.minidoorayaccount.domain.AccountTeamBundleUpdateRequest;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.exception.NotFoundAccountDetailsException;
import com.example.minidoorayaccount.exception.NotFoundAccountTeamBundleException;
import com.example.minidoorayaccount.exception.NotFoundTeamCodeException;
import com.example.minidoorayaccount.repository.AccountDetailsRepository;
import com.example.minidoorayaccount.repository.AccountTeamCodeBundleRepository;
import com.example.minidoorayaccount.repository.TeamCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAccountTeamBundleService implements AccountTeamBundleService {
    private final AccountTeamCodeBundleRepository bundleRepository;

    private final AccountDetailsRepository accountDetailsRepository;

    private final TeamCodeRepository teamCodeRepository;


    @Override
    public List<TeamCodeDtoImpl> getTeamNamesByAccountId(Integer accountId) {
        List<AccountTeamBundle> dto = bundleRepository.findByAccountDetails_AccountDetailsId(accountId);

        if(dto.isEmpty())
            throw new NotFoundAccountTeamBundleException();

        return dto.stream()
                .map(AccountTeamBundle::getTeamCode)
                .map(DefaultTeamCodeService::converterToDtoImpl)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamCodeDtoImpl> getTeamNamesByAccountName(String accountName) {
        List<AccountTeamBundle> dto = bundleRepository.findByAccountDetails_Name(accountName);

        if (dto.isEmpty())
            throw new NotFoundAccountTeamBundleException();

        return dto.stream()
                .map(AccountTeamBundle::getTeamCode)
                .map(DefaultTeamCodeService::converterToDtoImpl)
                .collect(Collectors.toList());
    }


    @Override
    public AccountTeamBundleDtoImpl createAccountTeamBundle(AccountTeamBundlePostRequest accountTeamBundleDto) {
        AccountTeamBundleDtoImpl dtoImpl = new AccountTeamBundleDtoImpl(new AccountTeamBundle.Pk(), null, null, null);
        dtoImpl.getPk().setTeamId(accountTeamBundleDto.getTeamId());
        dtoImpl.getPk().setAccountDetailsId(accountTeamBundleDto.getAccountId());
        AccountTeamBundle accountTeamBundle = converterToEntity(dtoImpl);
        bundleRepository.saveAndFlush(accountTeamBundle);
        return dtoImpl;
    }

    @Override
    @Transactional
    public AccountTeamBundleDtoImpl updateAccountTeamBundle(AccountTeamBundleUpdateRequest accountTeamBundleDto) {

        AccountTeamBundleDtoImpl dto = new AccountTeamBundleDtoImpl(new AccountTeamBundle.Pk(),
                accountDetailsRepository.getByAccountDetailsId(accountTeamBundleDto.getAccountId()),
                teamCodeRepository.findByTeamId(accountTeamBundleDto.getNewTeamId()),
                LocalDateTime.now());

        if (Objects.isNull(bundleRepository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(accountTeamBundleDto.getTeamId(), accountTeamBundleDto.getAccountId())))
            throw new NotFoundAccountTeamBundleException();

        if (Objects.isNull(accountTeamBundleDto.getNewTeamId())) {

            dto.getPk().setTeamId(accountTeamBundleDto.getTeamId());
            dto.getPk().setAccountDetailsId(accountTeamBundleDto.getNewAccountId());
            dto.setRegisterDate(LocalDateTime.now());

            bundleRepository.updateAccountTeamBundleByTeamId(dto, accountTeamBundleDto.getAccountId());
        }

        if (Objects.isNull(accountTeamBundleDto.getNewAccountId())) {

            dto.getPk().setAccountDetailsId(accountTeamBundleDto.getAccountId());
            dto.getPk().setTeamId(accountTeamBundleDto.getNewTeamId());
            dto.setRegisterDate(LocalDateTime.now());

            bundleRepository.updateAccountTeamBundleByAccountId(dto, accountTeamBundleDto.getTeamId());
        }

        return dto;
    }

    @Override
    @Transactional
    public AccountTeamBundlePostRequest deleteAccountTeamBundle(Integer deleteTeamId, Integer deleteAccountId) {
        bundleRepository.deleteBundle(deleteTeamId, deleteAccountId);
        AccountTeamBundlePostRequest dto = new AccountTeamBundlePostRequest();
        dto.setAccountId(deleteAccountId);
        dto.setTeamId(deleteTeamId);
        return dto;
    }


    public AccountTeamBundle converterToEntity(AccountTeamBundleDtoImpl teamBundleDto) {
        AccountTeamBundle accountTeamBundle = new AccountTeamBundle();

        accountTeamBundle.setPk(new AccountTeamBundle.Pk());
        accountTeamBundle.getPk().setAccountDetailsId(teamBundleDto.getPk().getAccountDetailsId());
        accountTeamBundle.getPk().setTeamId(teamBundleDto.getPk().getTeamId());

        accountTeamBundle.setAccountDetails(accountDetailsRepository.getByAccountDetailsId(teamBundleDto.getPk().getAccountDetailsId()));
        accountTeamBundle.setTeamCode(teamCodeRepository.findByTeamId(teamBundleDto.getPk().getTeamId()));
        accountTeamBundle.setRegisterDate(LocalDateTime.now());

        if (Objects.isNull(accountTeamBundle.getAccountDetails()))
            throw new NotFoundAccountDetailsException();

        if (Objects.isNull(accountTeamBundle.getTeamCode()))
            throw new NotFoundTeamCodeException();

        return accountTeamBundle;
    }

}
