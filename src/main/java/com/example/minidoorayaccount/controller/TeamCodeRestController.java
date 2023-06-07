package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.TeamCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TeamCodeRestController {

    private final TeamCodeService service;

    @GetMapping("/teams/{teamName}")
    public TeamCodeDtoImpl getTeamCodeByTeamName(@PathVariable("teamName") String teamName) {
        return service.getTeamCodeByTeamName(teamName);
    }

    @PostMapping("/teams")
    public TeamCodeDtoImpl postTeams(@Valid @RequestBody TeamCodeDtoImpl teamCodeDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createTeamCode(teamCodeDto);
    }

    @PutMapping("/teams")
    public TeamCodeDtoImpl putTeamsById(@Valid @RequestBody TeamCodeDtoImpl teamCodeDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.updateTeamCodeById(teamCodeDto);
    }

    @DeleteMapping("/teams/{teamId}")
    public Integer deleteTeamsById(@PathVariable("teamId") Integer deleteTeamId) {
        return service.deleteTeamCodeById(deleteTeamId);
    }

}
