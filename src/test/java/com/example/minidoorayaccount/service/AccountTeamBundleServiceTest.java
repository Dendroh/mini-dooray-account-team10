package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.entity.Account;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.entity.TeamCode;
import com.example.minidoorayaccount.exception.NotFoundAccountException;
import com.example.minidoorayaccount.exception.NotFoundAccountTeamBundleException;
import com.example.minidoorayaccount.repository.AccountDetailsRepository;
import com.example.minidoorayaccount.repository.AccountTeamCodeBundleRepository;
import com.example.minidoorayaccount.repository.TeamCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTeamBundleServiceTest {
    @Mock
    private AccountDetailsRepository detailsRepository;

    @Mock
    private TeamCodeRepository teamCodeRepository;

    @Mock
    private AccountTeamCodeBundleRepository bundleRepository;

    @InjectMocks
    private DefaultAccountTeamBundleService service;

    private ArrayList<AccountTeamBundle> bundleArrayList;

    private AccountTeamBundle bundle1;

    @BeforeEach
    public void setUp() {
        bundle1 = new AccountTeamBundle();
        bundleArrayList = new ArrayList<>();

        bundle1.setPk(new AccountTeamBundle.Pk());

        Account account1 = new Account();
        account1.setAccountId(1);
        account1.setEmail("testEmail");
        account1.setPassword("$2a$10$DiCK.AG5Oh2i5Zoxd4mtuOR3H4zBNuV5Xs0G33ohPlgST8FFyBlNy");

        AccountDetails accountDetails1 = new AccountDetails();
        accountDetails1.setAccountDetailsId(1);
        accountDetails1.setName("testName");
        accountDetails1.setImageFileName("testName.png");
        accountDetails1.setIsDormant(false);
        accountDetails1.setRegisterDate(LocalDateTime.now().plusHours(9));
        accountDetails1.setAccount(account1);

        TeamCode teamCode1 = new TeamCode();
        teamCode1.setTeamName("testTeam2");
        teamCode1.setTeamId(2);

        bundle1.getPk().setAccountDetailsId(1);
        bundle1.getPk().setTeamId(2);

        bundle1.setAccountDetails(accountDetails1);
        bundle1.setTeamCode(teamCode1);
        bundle1.setRegisterDate(LocalDateTime.now().plusHours(9));

        bundleArrayList.add(bundle1);
    }

    @Test
    void getTeamNamesByAccountId() {
        doReturn(bundleArrayList).when(bundleRepository).findByAccountDetails_AccountDetailsId(1);
        doReturn(List.of()).when(bundleRepository).findByAccountDetails_AccountDetailsId(2);

        List<TeamCodeDtoImpl> dtoList = service.getTeamNamesByAccountId(1);

        assertThat(dtoList).hasSize(1);
        assertThat(dtoList.get(0).getTeamName()).isEqualTo("testTeam2");

        Assertions.assertThrows(NotFoundAccountTeamBundleException.class, () -> service.getTeamNamesByAccountId(2));
    }

    @Test
    void getTeamNamesByAccountName() {
        doReturn(bundleArrayList).when(bundleRepository).findByAccountDetails_Name("testName");
        doReturn(List.of()).when(bundleRepository).findByAccountDetails_Name("notFoundName");

        List<TeamCodeDtoImpl> dtoList = service.getTeamNamesByAccountName("testName");

        assertThat(dtoList).hasSize(1);
        assertThat(dtoList.get(0).getTeamName()).isEqualTo("testTeam2");

        Assertions.assertThrows(NotFoundAccountTeamBundleException.class, () -> service.getTeamNamesByAccountName("notFoundName"));
    }

    @Test
    void getTeamNamesByAccountEmail() {
        AccountTeamBundle bundle = bundleArrayList.get(0);

        AccountDetailsDtoImpl dto = new AccountDetailsDtoImpl(bundle.getPk().getAccountDetailsId(), bundle.getAccountDetails().getName(), bundle.getAccountDetails().getImageFileName(),
                bundle.getAccountDetails().getIsDormant(), bundle.getAccountDetails().getRegisterDate());

        doReturn(dto).when(detailsRepository).findByAccount_Email("testEmail");
        doReturn(null).when(detailsRepository).findByAccount_Email("notFoundEmail");


        doReturn(bundleArrayList).when(bundleRepository).findByAccountDetails_AccountDetailsId(1);

        List<TeamCodeDtoImpl> dtoList = service.getTeamNamesByAccountEmail("testEmail");

        assertThat(dtoList.get(0)).isNotNull();
        assertThat(dtoList.get(0).getTeamName()).isEqualTo("testTeam2");


        Assertions.assertThrows(NotFoundAccountException.class, () -> service.getTeamNamesByAccountEmail("notFoundEmail"));
    }

    @Test
    void createAccountTeamBundle() {
        AccountTeamCodeBundlePostReq postReq = new AccountTeamCodeBundlePostReq();
        postReq.setEmail("testEmail");
        postReq.setTeamName("testTeam");

        doReturn(bundle1.getAccountDetails()).when(detailsRepository).getByAccount_Email("testEmail");

        doReturn(bundle1.getTeamCode()).when(teamCodeRepository).findByTeamName("testTeam");


        doReturn(bundle1.getAccountDetails()).when(detailsRepository).getByAccountDetailsId(1);
        doReturn(bundle1.getTeamCode()).when(teamCodeRepository).findByTeamId(2);

        AccountTeamBundleRespDto respDto = service.createAccountTeamBundle(postReq);

        assertThat(respDto.getAccountEmail()).isEqualTo("testEmail");
        assertThat(respDto.getTeamName()).isEqualTo("testTeam2");


        verify(bundleRepository, times(1)).saveAndFlush(any(AccountTeamBundle.class));
    }

    @Test
    void updateAccountTeamBundle() {
        AccountTeamBundleUpdateReq updateReq1 = new AccountTeamBundleUpdateReq();
        updateReq1.setEmail("testEmail");
        updateReq1.setTeamName("testTeam2");
        updateReq1.setNewEmail("newExam");

        Account account2 = new Account();
        account2.setAccountId(2);
        account2.setEmail("testEmail2");
        account2.setPassword("testPassword2");

        AccountDetails accountDetails2 = new AccountDetails();
        accountDetails2.setAccountDetailsId(account2.getAccountId());
        accountDetails2.setAccount(account2);
        accountDetails2.setName("test2");
        accountDetails2.setIsDormant(true);
        accountDetails2.setRegisterDate(LocalDateTime.now().plusHours(9));

        TeamCode teamCode2 = new TeamCode();
        teamCode2.setTeamId(3);
        teamCode2.setTeamName("testTeam3");

        AccountTeamBundle bundle2 = new AccountTeamBundle();
        bundle2.setPk(new AccountTeamBundle.Pk());
        bundle2.setTeamCode(teamCode2);
        bundle2.setAccountDetails(accountDetails2);
        bundle2.setRegisterDate(LocalDateTime.now().plusHours(9));
        bundle2.getPk().setTeamId(teamCode2.getTeamId());
        bundle2.getPk().setAccountDetailsId(accountDetails2.getAccountDetailsId());

        doReturn(accountDetails2).when(detailsRepository).getByAccount_Email(updateReq1.getNewEmail());
        doReturn(bundle1.getAccountDetails()).when(detailsRepository).getByAccount_Email(bundle1.getAccountDetails().getAccount().getEmail());

        doReturn(bundle1.getTeamCode()).when(teamCodeRepository).findByTeamName("testTeam2");

        doReturn(bundle1).when(bundleRepository).findByAccountDetails_NameAndTeamCode_TeamName(bundle1.getAccountDetails().getName(), bundle1.getTeamCode().getTeamName());

        doReturn(bundle2).when(bundleRepository).saveAndFlush(any(AccountTeamBundle.class));

        AccountTeamBundleRespDto result = service.updateAccountTeamBundle(updateReq1);

        assertThat(result).isNotNull();
        assertThat(result.getTeamName()).isEqualTo(teamCode2.getTeamName());
        assertThat(result.getAccountEmail()).isEqualTo(accountDetails2.getAccount().getEmail());
    }

    @Test
    void deleteAccountTeamBundle() {
        doReturn(bundle1.getAccountDetails()).when(detailsRepository).getByAccount_Email(bundle1.getAccountDetails().getAccount().getEmail());
        doReturn(bundle1.getTeamCode()).when(teamCodeRepository).findByTeamName(bundle1.getTeamCode().getTeamName());

        service.deleteAccountTeamBundle(bundle1.getTeamCode().getTeamName(), bundle1.getAccountDetails().getAccount().getEmail());

        verify(bundleRepository, times(1)).deleteByPk_TeamIdAndPk_AccountDetailsId(bundle1.getTeamCode().getTeamId(), bundle1.getAccountDetails().getAccountDetailsId());
    }
}