package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.AccountDetailsDtoImpl;
import com.example.minidoorayaccount.domain.AccountDetailsPostReq;
import com.example.minidoorayaccount.domain.AccountDetailsUpdateReq;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/accountDetails/email/{email}")
    public AccountDetailsDtoImpl getAccountDetailByEmail(@PathVariable("email") String email) {
        return service.getAccountDetailByEmail(email);
    }

    @PostMapping("/accountDetails/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountDetailsDtoImpl postAccountDetail(@Valid @RequestBody AccountDetailsPostReq accountDetailsDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createAccountDetail(accountDetailsDto);
    }

    @PutMapping("/accountDetails/")
    public AccountDetailsDtoImpl putAccountDetail(@Valid @RequestBody AccountDetailsUpdateReq accountDetailsDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.modifyAccountDetail(accountDetailsDto);
    }

    @DeleteMapping("/accountDetails/{deleteAccountEmail}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAccountDetail(@PathVariable("deleteAccountEmail") String email) {
        service.deleteAccountDetail(email);
    }

}
