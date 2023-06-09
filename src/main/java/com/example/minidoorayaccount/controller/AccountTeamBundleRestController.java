package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.entity.AccountTeamBundle;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.AccountTeamBundleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountTeamBundleRestController {
    private final AccountTeamBundleService service;

    @GetMapping("/accountTeams/id/{accountId}")
    public List<TeamCodeDtoImpl> getTeamNamesByAccountId(@PathVariable("accountId") Integer accountId) {
        return service.getTeamNamesByAccountId(accountId);
    }


    @GetMapping("/accountTeams/name/{accountName}")
    public List<TeamCodeDtoImpl> getTeamNamesByAccountName(@PathVariable("accountName") String accountName) {
        return service.getTeamNamesByAccountName(accountName);
    }


    @PostMapping("/accountTeams/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountTeamBundleReqDto postAccountTeamBundle(@Valid @RequestBody AccountTeamBundleReqDto accountTeamBundleDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createAccountTeamBundle(accountTeamBundleDto);
    }

    @PutMapping("/accountTeams/")
    public AccountTeamBundleReqDto putAccountTeamBundle(@Valid @RequestBody AccountTeamBundleUpdateRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.updateAccountTeamBundle(request);
    }

    @DeleteMapping("/accountTeams/{deleteTeamId}/{deleteAccountId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public AccountTeamBundleReqDto deleteAccountTeamBundle(@PathVariable("deleteTeamId") Integer deleteTeamId, @PathVariable("deleteAccountId") Integer deleteAccountId) {
        return service.deleteAccountTeamBundle(deleteTeamId, deleteAccountId);
    }

}
