package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.domain.TeamCodeUpdateReq;
import com.example.minidoorayaccount.exception.ValidationFailedException;
import com.example.minidoorayaccount.service.TeamCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/teams/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeamCodeDtoImpl postTeams(@Valid @RequestBody TeamCodeDtoImpl teamCodeDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.createTeamCode(teamCodeDto);
    }

    @PutMapping("/teams/")
    public TeamCodeDtoImpl putTeamsById(@Valid @RequestBody TeamCodeUpdateReq teamCodeDto, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationFailedException(result);

        return service.updateTeamCodeById(teamCodeDto);
    }

    @DeleteMapping("/teams/{deleteTeamName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTeamsById(@PathVariable("deleteTeamName") String deleteTeamName) {
        service.deleteTeamCodeById(deleteTeamName);
    }

}
