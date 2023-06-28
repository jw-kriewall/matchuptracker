package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.service.MatchupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    private Matchup sampleMatchup1;
    private Matchup sampleMatchup2;

    @BeforeEach
    public void init() {
        sampleMatchup1 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").playerOneDeck("Pikachu")
                .playerTwoDeck("Squirtle").winningDeck("Pikachu").format("Standard").notes("none").build();
        sampleMatchup2 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").playerOneDeck("Pikachu")
                .playerTwoDeck("Pikachu").winningDeck("none").format("Standard").notes("none").build();
    }

    @Test
    @DisplayName("Does getAll method return all matchups?")
    public void testGetAll() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup1);
        matchups.add(sampleMatchup2);

        when(mockService.getAllMatchups()).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get("/matchups/getAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchups)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("When a matchup saves, do we return the correct object?")
    void testAddMatchup() throws Exception {
        given(mockService.saveMatchup(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/matchups/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleMatchup1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerOneDeck", CoreMatchers.is(sampleMatchup1.getPlayerOneDeck())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.format", CoreMatchers.is(sampleMatchup1.getFormat())));
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