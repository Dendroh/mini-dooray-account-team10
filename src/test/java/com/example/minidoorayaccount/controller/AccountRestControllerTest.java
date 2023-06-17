package com.example.minidoorayaccount.controller;

import com.example.minidoorayaccount.domain.*;
import com.example.minidoorayaccount.service.AccountDetailsService;
import com.example.minidoorayaccount.service.AccountService;
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
class AccountRestControllerTest {

    @MockBean
    private AccountService service;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAccounts() throws Exception {
        given(service.getAccounts()).willReturn(List.of(new AccountDtoImpl(1, "test1", "test1Password")));

        mockMvc.perform(get("/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("test1"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAccountByEmail() throws Exception {
        given(service.getAccountByEmail("testEmail")).willReturn(new AccountDtoImpl(1, "test1", "test1Password"));

        mockMvc.perform(get("/accounts/email/{email}", "testEmail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(1))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAccountById() throws Exception {
        given(service.getAccountById(1)).willReturn(new AccountDtoImpl(1, "test1", "test1Password"));

        mockMvc.perform(get("/accounts/{accountId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test1"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void postAccount() throws Exception {
        AccountDtoImpl accountDto = new AccountDtoImpl(1, "test", "testPassword");

        given(service.createAccount(any(AccountDtoImpl.class))).willReturn(accountDto);

        mockMvc.perform(post("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test"))
                .andDo(print())
                .andReturn();

        accountDto.setEmail(null);

        mockMvc.perform(post("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

        accountDto.setEmail("null");
        accountDto.setPassword(null);

        mockMvc.perform(post("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void putAccount() throws Exception {
        AccountUpdateReq updateReq = new AccountUpdateReq();
        updateReq.setBeforeEmail("test");
        updateReq.setAfterEmail("after");
        updateReq.setPassword("password");

        AccountDtoImpl accountDto = new AccountDtoImpl(1, updateReq.getAfterEmail(), updateReq.getPassword());

        given(service.modifyAccount(any(AccountUpdateReq.class))).willReturn(accountDto);

        mockMvc.perform(put("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(updateReq.getAfterEmail()))
                .andDo(print())
                .andReturn();


        updateReq.setBeforeEmail(null);

        mockMvc.perform(put("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();

        updateReq.setBeforeEmail("test");
        updateReq.setAfterEmail(null);

        mockMvc.perform(put("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteAccountById() throws Exception {
        mockMvc.perform(delete("/accounts/{deleteEmail}", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).deleteAccount(anyString());

    }
}