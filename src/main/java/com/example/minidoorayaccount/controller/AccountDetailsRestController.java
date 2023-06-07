package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountDetailsRestController {

    private final AccountDetailsService service;

    @GetMapping("/accountDetails")
    public List<AccountDetailsDtoImpl> getAccountDetails() {
        return service.getAccountDetails();
    }

    @GetMapping("/accountDetails/{accountId}")
    public AccountDetailsDtoImpl getAccountDetailById(@PathVariable("accountId") Integer accountId) {
        return service.getAccountDetailById(accountId);
    }

    @GetMapping("/accountDetails/name/{detailsName}")
    public AccountDetailsDtoImpl getAccountDetailByName(@PathVariable("detailsName") String detailsName) {
        return service.getAccountDetailByName(detailsName);
    }

    @PostMapping("/accountDetails/")
    public AccountDetailsDtoImpl postAccountDetail(@Valid @RequestBody AccountDetailsDtoImpl accountDetailsDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createAccountDetail(accountDetailsDto);
    }

    @PutMapping("/accountDetails/")
    public AccountDetailsDtoImpl putAccountDetail(@Valid @RequestBody AccountDetailsDtoImpl accountDetailsDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.modifyAccountDetail(accountDetailsDto);
    }

    @DeleteMapping("/accountDetails/{deleteAccountDetailId}")
    public AccountDetailsDtoImpl deleteAccountDetail(@PathVariable("deleteAccountDetailId") Integer deleteId) {
        return service.deleteAccountDetail(deleteId);
    }

}
