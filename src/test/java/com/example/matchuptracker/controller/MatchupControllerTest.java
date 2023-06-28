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

@WebMvcTest(controllers = MatchupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MatchupControllerTest {

    //https://www.youtube.com/watch?v=BZBFw6fBeIU&list=PL82C6-O4XrHcg8sNwpoDDhcxUCbFy855E&index=8

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MatchupService mockService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() {
//        given(service.getAllMatchups()).willAnswer((invocation -> invocation.getArgument(0)));
//
//        ResultActions response = mockMvc.perform(get("/matchups/getAll"))
    }


//    @Test
//    void add() throws Exception {
//        given(mockService.saveMatchup(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
//
//        ResultActions response = mockMvc.perform(post("matchups/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString()));
//    }

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

    @Test
    void getMatchupPercentagesByDeckName() {
    }

    @Test
    void getTotalMatchesByDeck() {
    }

    @Test
    void getIndividualRecordsByDeck() {
    }
}