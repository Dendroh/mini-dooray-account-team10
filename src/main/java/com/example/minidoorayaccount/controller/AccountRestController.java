package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.domain.AccountUpdateReq;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService service;

    @GetMapping("/accounts")
    public List<AccountDto> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/accounts/email/{accountEmail}")
    public AccountDtoImpl getAccountByEmail(@PathVariable("accountEmail") String accountEmail) {
        return service.getAccountByEmail(accountEmail);
    }

    @GetMapping("/accounts/{accountId}")
    public AccountDtoImpl getAccountById(@PathVariable("accountId") Integer accountId) {
        return service.getAccountById(accountId);
    }

    @PostMapping("/accounts/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDtoImpl postAccount(@Valid @RequestBody AccountDtoImpl accountDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createAccount(accountDto);
    }

    @PutMapping("/accounts/")
    public AccountDtoImpl putAccount(@Valid @RequestBody AccountUpdateReq accountDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.modifyAccount(accountDto);
    }

    @DeleteMapping("/accounts/{deleteEmail}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAccountById(@PathVariable("deleteEmail") String deleteEmail) {
        service.deleteAccount(deleteEmail);
    }

}
