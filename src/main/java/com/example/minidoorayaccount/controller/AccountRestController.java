package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.AccountDto;
import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService service;

    @GetMapping("/accounts")
    public List<AccountDto> getAccounts() {
        return service.getAccounts();
    }

//    @GetMapping("/accounts/{accountEmail}")
//    public AccountDtoImpl getAccountByEmail(@PathVariable("accountEmail") String accountEmail) {
//
//    }

}
