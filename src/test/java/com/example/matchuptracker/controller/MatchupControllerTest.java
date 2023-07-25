package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Deck;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    @Autowired
    private MatchupController mockController;

    private Matchup sampleMatchup1;
    private Matchup sampleMatchup2;

//    private Deck sampleDeck1 = Deck.builder().name("DeckName").cards("Cards");

    private Deck sampleDeckPikachu = Deck.builder().name("Pikachu").cards("Cards").build();
    private Deck sampleDeckSquirtle = Deck.builder().name("Squirtle").cards("Cards").build();

    @BeforeEach
    public void init() {
        sampleMatchup1 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").playerOneDeck(sampleDeckPikachu)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck("Pikachu").format("Standard").notes("none").build();
        sampleMatchup2 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").playerOneDeck(sampleDeckPikachu)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck("none").format("Standard").notes("none").build();
    }

    @Test
    @DisplayName("Does getAll method return all matchups?")
    public void testGetAll() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup1);
        matchups.add(sampleMatchup2);

        when(mockService.getAllMatchups()).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_ALL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchups)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.playerOneDeck", CoreMatchers.containsString(sampleMatchup1.getPlayerOneDeck())));
    }


    @Test
    @DisplayName("When a matchup saves, do we return the correct object?")
    void testAddMatchup() throws Exception {
        given(mockService.saveMatchup(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post(MatchupController.MATCHUPS + MatchupController.ENDPOINT_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleMatchup1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerOneDeck.name", CoreMatchers.is(sampleMatchup1.getPlayerOneDeck().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.format", CoreMatchers.is(sampleMatchup1.getFormat())));
    }

    @Test
    void testUpdateMatchup() throws Exception {
        // Question -> below test only works if parameters are ArgumentMatchers.any() and not specific parameters (i.e. id = 1, matchup = sampleMatchup1). Why?
        when(mockService.updateMatchup(ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenReturn(sampleMatchup1);

        ResultActions response = mockMvc.perform(put(MatchupController.MATCHUPS + MatchupController.ENDPOINT_UPDATE + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleMatchup1)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerOneDeck.name", CoreMatchers.is(sampleMatchup1.getPlayerOneDeck().getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerTwoName", CoreMatchers.is(sampleMatchup1.getPlayerTwoName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.format", CoreMatchers.is(sampleMatchup1.getFormat())));
    }

    @Test
    void getMatchupById() throws Exception {
        when(mockService.findMatchupById(ArgumentMatchers.anyInt())).thenReturn(sampleMatchup2);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_ID + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleMatchup2)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerTwoName", CoreMatchers.is(sampleMatchup2.getPlayerTwoName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winningDeck", CoreMatchers.is(sampleMatchup2.getWinningDeck())));

    }

    @Test
    void getMatchupByDeckName() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString())).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_DECKNAME + "/dummyDeckName"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    void getMatchupsByPlayerName() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString())).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_USERNAME + "/dummyName"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    void getMatchupsByFormat() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString())).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_MATCHUPS_BY_FORMAT + "/dummyName"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    public void testGetMatchupPercentagesByDeckName() {
        // Arrange
        String deckName = "testDeck";
        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put("matchup1", 0.5);
        expectedResult.put("matchup2", 0.75);
        expectedResult.put("matchup3", 1.0);
        when(mockService.getMatchupPercentagesByDeckName(deckName)).thenReturn(expectedResult);

        // Act
        Map<String, Double> response = mockController.getMatchupPercentagesByDeckName(deckName);

        // Assert
        assertEquals(expectedResult, response);
    }


//    @Test
//    void getMatchupPercentagesByDeckName() {
//    }
//
//    @Test
//    void getTotalMatchesByDeck() {
//    }
//
//    @Test
//    void getIndividualRecordsByDeck() {
//    }
}