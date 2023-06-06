package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.repository.AccountTeamCodeBundleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAccountTeamBundleService {
    private AccountTeamCodeBundleRepository repository;
}
