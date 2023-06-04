package com.example.minidoorayaccount.service;

import com.example.minidoorayaccount.repository.TeamCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTeamCodeService {
    private final TeamCodeRepository repository;



}
