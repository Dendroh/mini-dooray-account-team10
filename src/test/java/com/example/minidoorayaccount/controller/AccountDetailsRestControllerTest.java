package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.entity.AccountDetails;
import com.example.minidoorayaccount.service.AccountDetailsService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AccountDetailsRestControllerTest {

    @MockBean
    private AccountDetailsService service;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAccountDetails() throws Exception {
        given(service.getAccountDetails()).willReturn(List.of(new AccountDetailsDtoImpl(1, "test1", "test1.png", false, LocalDateTime.now().plusHours(9))));

        mockMvc.perform(get("/accountDetails"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("test1"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAccountDetailById() throws Exception {
        given(service.getAccountDetailById(1)).willReturn(new AccountDetailsDtoImpl(1, "test1", null, false, LocalDateTime.now().plusHours(9)));

        mockMvc.perform(get("/accountDetails/{accountId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isDormant").value(false))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAccountDetailByName() throws Exception {
        given(service.getAccountDetailByName("test1")).willReturn(new AccountDetailsDtoImpl(1, "test1", null, true, LocalDateTime.now().plusHours(9)));

        mockMvc.perform(get("/accountDetails/name/{detailsName}", "test1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.imageFileName").doesNotExist())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAccountDetailByEmail() throws Exception {
        given(service.getAccountDetailByEmail("testEmail")).willReturn(new AccountDetailsDtoImpl(1, "test1", null, true, LocalDateTime.now().plusHours(9)));

        mockMvc.perform(get("/accountDetails/email/{email}", "testEmail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountDetailsId").value(1))
                .andDo(print())
                .andReturn();
    }

    @Test
    void postAccountDetail() throws Exception {
        AccountDetailsPostReq postReq = new AccountDetailsPostReq();
        postReq.setAccountEmail("testEmail");
        postReq.setName("test");

        AccountDetailsDtoImpl detailsDto = new AccountDetailsDtoImpl(1, "test", "test.png", false, LocalDateTime.now());

        given(service.createAccountDetail(any(AccountDetailsPostReq.class))).willReturn(detailsDto);

        mockMvc.perform(post("/accountDetails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postReq)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test"))
                .andDo(print())
                .andReturn();

        postReq.setAccountEmail(null);

        mockMvc.perform(post("/accountDetails/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void putAccountDetail() throws Exception {
        AccountDetailsUpdateReq updateReq = new AccountDetailsUpdateReq();
        updateReq.setAccountEmail("testEmail");
        updateReq.setIsDormant(true);
        updateReq.setName("test");

        AccountDetailsDtoImpl detailsDto = new AccountDetailsDtoImpl(1, updateReq.getName(), "test.png",
                updateReq.getIsDormant(), LocalDateTime.now());

        given(service.modifyAccountDetail(any(AccountDetailsUpdateReq.class))).willReturn(detailsDto);

        mockMvc.perform(put("/accountDetails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(updateReq.getName()))
                .andDo(print())
                .andReturn();


        updateReq.setAccountEmail(null);

        mockMvc.perform(put("/accountDetails/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteAccountDetail() throws Exception {
        mockMvc.perform(delete("/accountDetails/{deleteAccountEmail}", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).deleteAccountDetail(anyString());
    }
}