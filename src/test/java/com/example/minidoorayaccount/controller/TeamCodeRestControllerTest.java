package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.AccountDtoImpl;
import com.example.minidoorayaccount.domain.AccountUpdateReq;
import com.example.minidoorayaccount.domain.TeamCodeDtoImpl;
import com.example.minidoorayaccount.domain.TeamCodeUpdateReq;
import com.example.minidoorayaccount.service.TeamCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TeamCodeRestControllerTest {

    @MockBean
    private TeamCodeService service;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getTeamCodeByTeamName() throws Exception {
        given(service.getTeamCodeByTeamName("team")).willReturn(new TeamCodeDtoImpl(1, "team"));

        mockMvc.perform(get("/teams/{teamName}", "team"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamName").value("team"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void postTeams() throws Exception {
        TeamCodeDtoImpl teamCodeDto = new TeamCodeDtoImpl(1, "test");

        given(service.createTeamCode(any(TeamCodeDtoImpl.class))).willReturn(teamCodeDto);

        mockMvc.perform(post("/teams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamCodeDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamName").value("test"))
                .andDo(print())
                .andReturn();

        teamCodeDto.setTeamName(null);

        mockMvc.perform(post("/teams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamCodeDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void putTeamsById() throws Exception {
        TeamCodeUpdateReq updateReq = new TeamCodeUpdateReq();
        updateReq.setBeforeTeamName("test");
        updateReq.setAfterTeamName("after");

        TeamCodeDtoImpl teamCodeDto = new TeamCodeDtoImpl(1, updateReq.getAfterTeamName());

        given(service.updateTeamCodeById(any(TeamCodeUpdateReq.class))).willReturn(teamCodeDto);

        mockMvc.perform(put("/teams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.teamName").value(updateReq.getAfterTeamName()))
                .andDo(print())
                .andReturn();


        updateReq.setBeforeTeamName(null);

        mockMvc.perform(put("/teams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

        updateReq.setBeforeTeamName("null");
        updateReq.setAfterTeamName(null);

        mockMvc.perform(put("/teams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteTeamsById() throws Exception {
        mockMvc.perform(delete("/teams/{deleteTeamName}", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).deleteTeamCodeById("test");
    }
}