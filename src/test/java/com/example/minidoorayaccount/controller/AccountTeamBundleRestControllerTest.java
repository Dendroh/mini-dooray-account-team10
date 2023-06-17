package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.service.AccountTeamBundleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AccountTeamBundleRestControllerTest {

    @MockBean
    private AccountTeamBundleService service;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getTeamNamesByAccountId() throws Exception {
        given(service.getTeamNamesByAccountId(1)).willReturn(List.of(new TeamCodeDtoImpl(1, "team1"),
                new TeamCodeDtoImpl(2, "team2")));

        mockMvc.perform(get("/accountTeams/id/{accountId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].teamName").value("team2"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getTeamNamesByAccountName() throws Exception {
        given(service.getTeamNamesByAccountName("test")).willReturn(List.of(new TeamCodeDtoImpl(1, "team1"),
                new TeamCodeDtoImpl(2, "team2")));

        mockMvc.perform(get("/accountTeams/name/{accountName}", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].teamId").value(2))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getTeamNamesByAccountEmail() throws Exception {
        given(service.getTeamNamesByAccountEmail("testEmail")).willReturn(List.of(new TeamCodeDtoImpl(1, "team1"),
                new TeamCodeDtoImpl(2, "team2")));

        mockMvc.perform(get("/accountTeams/email/{accountEmail}", "testEmail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].teamName").value("team1"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void postAccountTeamBundle() throws Exception {
        AccountTeamCodeBundlePostReq accountTeamBundleDto = new AccountTeamCodeBundlePostReq();
        accountTeamBundleDto.setEmail("test1");
        accountTeamBundleDto.setTeamName("team1");

        AccountTeamBundleRespDto respDto = new AccountTeamBundleRespDto(1, 2, LocalDateTime.now(), accountTeamBundleDto.getTeamName(),
                accountTeamBundleDto.getEmail());

        given(service.createAccountTeamBundle(any(AccountTeamCodeBundlePostReq.class))).willReturn(respDto);

        mockMvc.perform(post("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTeamBundleDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountEmail").value("test1"))
                .andDo(print())
                .andReturn();

        accountTeamBundleDto.setEmail(null);

        mockMvc.perform(post("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTeamBundleDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

        accountTeamBundleDto.setEmail("null");
        accountTeamBundleDto.setTeamName(null);

        mockMvc.perform(post("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTeamBundleDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void putAccountTeamBundle() throws Exception {
        AccountTeamBundleUpdateReq updateReq = new AccountTeamBundleUpdateReq();
        updateReq.setEmail("testEmail");
        updateReq.setTeamName("testTeam");
        updateReq.setNewTeamName("newTeamName");

        AccountTeamBundleRespDto accountDto = new AccountTeamBundleRespDto(2, 2, LocalDateTime.now(),
                updateReq.getNewTeamName(), updateReq.getEmail());

        given(service.updateAccountTeamBundle(any(AccountTeamBundleUpdateReq.class))).willReturn(accountDto);

        mockMvc.perform(put("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountEmail").value(updateReq.getEmail()))
                .andExpect(jsonPath("$.teamName").value(updateReq.getNewTeamName()))
                .andDo(print())
                .andReturn();


        updateReq.setEmail(null);

        mockMvc.perform(put("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

        updateReq.setEmail("test");
        updateReq.setTeamName(null);

        mockMvc.perform(put("/accountTeams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

    }

    @Test
    void deleteAccountTeamBundle() throws Exception {
        mockMvc.perform(delete("/accountTeams/{deleteTeamName}/{deleteAccountEmail}", "team", "testEmail"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).deleteAccountTeamBundle("team", "testEmail");
    }
}