package com.example.matchuptracker.controller;

import com.example.matchuptracker.service.MatchupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = MatchupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MatchupControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MatchupService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() {
//        given(service.getAllMatchups()).willAnswer((invocation -> invocation.getArgument(0)));
//
//        ResultActions response = mockMvc.perform(get("/matchups/getAll"))
    }

    @Test
    void testAdd() {
    }

    @Test
    void updateMatchup() {
    }

    @Test
    void getMatchupById() {
    }

    @Test
    void getMatchupByDeckName() {
    }

    @Test
    void getMatchupsByPlayerName() {
    }

    @Test
    void getMatchupsByFormat() {
    }
}