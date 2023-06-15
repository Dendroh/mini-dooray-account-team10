package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;
import com.example.minidoorayaccount.exception.NotFoundAccountDetailsException;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
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
    public List<TeamCodeDtoImpl> getTeamNamesByAccountEmail(String accountEmail) {
        AccountDetailsDtoImpl accountByEmail = accountDetailsRepository.findByAccount_Email(accountEmail);

        if (Objects.isNull(accountByEmail))
            throw new NotFoundAccountException();


        return bundleRepository.findByAccountDetails_AccountDetailsId(accountByEmail.getAccountDetailsId()).stream()
                .map(AccountTeamBundle::getTeamCode)
                .map(DefaultTeamCodeService::converterToDtoImpl)
                .collect(Collectors.toList());
    }


    @Override
    public AccountTeamBundleRespDto createAccountTeamBundle(AccountTeamCodeBundlePostReq accountTeamBundleDto) {

        AccountDetails accountDetails = checkNullAndGetAccountDetailsByEmail(accountTeamBundleDto.getEmail());
        TeamCode teamCode = checkNullAndGetTeamCodeByTeamName(accountTeamBundleDto.getTeamName());

        AccountTeamBundleDtoImpl dtoImpl = new AccountTeamBundleDtoImpl(new AccountTeamBundle.Pk(),
                accountDetails, teamCode, LocalDateTime.now().plusHours(9));

        dtoImpl.getPk().setTeamId(teamCode.getTeamId());
        dtoImpl.getPk().setAccountDetailsId(accountDetails.getAccountDetailsId());

        AccountTeamBundle accountTeamBundle = converterToEntity(dtoImpl);
        bundleRepository.saveAndFlush(accountTeamBundle);

        return new AccountTeamBundleRespDto(null, null, dtoImpl.getRegisterDate(),
                dtoImpl.getTeamCode().getTeamName(), dtoImpl.getAccountDetails().getAccount().getEmail());
    }

    @Override
    public AccountTeamBundleRespDto updateAccountTeamBundle(AccountTeamBundleUpdateReq accountTeamBundleDto) {

        AccountDetails accountDetails = checkNullAndGetAccountDetailsByEmail(accountTeamBundleDto.getEmail());
        TeamCode teamCode = checkNullAndGetTeamCodeByTeamName(accountTeamBundleDto.getTeamName());
        AccountTeamBundle deleteBundle = bundleRepository.findByAccountDetails_NameAndTeamCode_TeamName(accountDetails.getName(), teamCode.getTeamName());

        if (Objects.isNull(bundleRepository.queryByTeamCode_TeamIdAndAccountDetails_AccountDetailsId(teamCode.getTeamId(), accountDetails.getAccountDetailsId())))
            throw new NotFoundAccountTeamBundleException();

        if (Objects.isNull(accountTeamBundleDto.getNewTeamName())) {
            AccountDetails accountDetailsByNewEmail = checkNullAndGetAccountDetailsByEmail(accountTeamBundleDto.getNewEmail());

            bundleRepository.deleteById(deleteBundle.getPk());

            AccountTeamBundle newBundle = new AccountTeamBundle();
            newBundle.setPk(new AccountTeamBundle.Pk());
            newBundle.setTeamCode(teamCode);
            newBundle.setAccountDetails(accountDetailsByNewEmail);
            newBundle.getPk().setTeamId(teamCode.getTeamId());
            newBundle.getPk().setAccountDetailsId(accountDetails.getAccountDetailsId());
            newBundle.setRegisterDate(LocalDateTime.now().plusHours(9));

            return convertToResp(bundleRepository.saveAndFlush(newBundle));
        }

        else {
            TeamCode teamCodeByNewTeamName = checkNullAndGetTeamCodeByTeamName(accountTeamBundleDto.getNewTeamName());

            bundleRepository.deleteById(deleteBundle.getPk());

            AccountTeamBundle newBundle = new AccountTeamBundle();
            newBundle.setPk(new AccountTeamBundle.Pk());
            newBundle.setTeamCode(teamCodeByNewTeamName);
            newBundle.setAccountDetails(accountDetails);
            newBundle.getPk().setTeamId(teamCodeByNewTeamName.getTeamId());
            newBundle.getPk().setAccountDetailsId(accountDetails.getAccountDetailsId());
            newBundle.setRegisterDate(LocalDateTime.now().plusHours(9));

            return convertToResp(bundleRepository.saveAndFlush(newBundle));
        }
    }

    @Override
    public void deleteAccountTeamBundle(String deleteTeamName, String deleteAccountEmail) {
        TeamCode teamCode = checkNullAndGetTeamCodeByTeamName(deleteTeamName);
        AccountDetails accountDetails = checkNullAndGetAccountDetailsByEmail(deleteAccountEmail);

        bundleRepository.deleteByPk_TeamIdAndPk_AccountDetailsId(teamCode.getTeamId(), accountDetails.getAccountDetailsId());
    }


    public AccountTeamBundle converterToEntity(AccountTeamBundleDtoImpl teamBundleDto) {
        AccountTeamBundle accountTeamBundle = new AccountTeamBundle();

        accountTeamBundle.setPk(new AccountTeamBundle.Pk());
        accountTeamBundle.getPk().setAccountDetailsId(teamBundleDto.getPk().getAccountDetailsId());
        accountTeamBundle.getPk().setTeamId(teamBundleDto.getPk().getTeamId());

        accountTeamBundle.setAccountDetails(accountDetailsRepository.getByAccountDetailsId(teamBundleDto.getPk().getAccountDetailsId()));
        accountTeamBundle.setTeamCode(teamCodeRepository.findByTeamId(teamBundleDto.getPk().getTeamId()));
        accountTeamBundle.setRegisterDate(teamBundleDto.getRegisterDate());

        if (Objects.isNull(accountTeamBundle.getAccountDetails()))
            throw new NotFoundAccountDetailsException();

        if (Objects.isNull(accountTeamBundle.getTeamCode()))
            throw new NotFoundTeamCodeException();

        return accountTeamBundle;
    }

    private AccountDetails checkNullAndGetAccountDetailsByEmail(String email) {
        AccountDetails accountDetails = accountDetailsRepository.getByAccount_Email(email);

        if (Objects.isNull(accountDetails))
            throw new NotFoundAccountDetailsException();

        return accountDetails;
    }

    private TeamCode checkNullAndGetTeamCodeByTeamName(String teamName) {
        TeamCode teamCode = teamCodeRepository.findByTeamName(teamName);

        if (Objects.isNull(teamCode))
            throw new NotFoundTeamCodeException();

        return teamCode;
    }

    public static AccountTeamBundleRespDto convertToResp(AccountTeamBundle bundle) {
        return new AccountTeamBundleRespDto(bundle.getPk().getTeamId(), bundle.getPk().getAccountDetailsId(),
                bundle.getRegisterDate(), bundle.getTeamCode().getTeamName(), bundle.getAccountDetails().getAccount().getEmail());
    }

}
