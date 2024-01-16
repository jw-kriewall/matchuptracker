package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Deck;
import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.repository.MatchupRepository;
import com.example.matchuptracker.service.matchup.MatchupService;
import com.example.matchuptracker.service.matchup.MatchupServiceImpl;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utils.Constants;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    @MockBean
    private JwtDecoder jwtDecoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MatchupController mockController;
    @MockBean
    private MatchupRepository mockRepository;

    private Matchup sampleMatchup1;
    private Matchup sampleMatchup2;
    private Matchup sampleMatchup3;

//    private Deck sampleDeck1 = Deck.builder().name("DeckName").cards("Cards");

    private Deck sampleDeckPikachu = Deck.builder().name(Constants.PIKACHU).cards("Cards").build();
    private Deck sampleDeckSquirtle = Deck.builder().name(Constants.SQUIRTLE).cards("Cards").build();
    private Deck sampleDeckCharizard = Deck.builder().name(Constants.CHARIZARD).cards("Cards").build();

    private String email = "123@gmail.com";
    private String password = "password";
    User dummyUser = User.builder().email(email).build();

    Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("email", email)
            .build();
    JwtAuthenticationToken mockJWTAuth = new JwtAuthenticationToken(jwt);

    @BeforeEach
    public void init() {
        sampleMatchup1 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").createdBy(dummyUser).playerOneDeck(sampleDeckPikachu)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck(Constants.PIKACHU).format("Standard").notes("none").build();
        sampleMatchup2 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").createdBy(dummyUser).playerOneDeck(sampleDeckPikachu)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck("none").format("Standard").notes("none").build();
        sampleMatchup3 = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").createdBy(dummyUser).playerOneDeck(sampleDeckCharizard)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck(Constants.CHARIZARD).format("Standard").notes("none").build();
    }

    @Test
    @DisplayName("Does getAll method return all matchups?")
    public void testGetAll() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup1);
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup3);

        when(mockService.getAllMatchups()).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_ALL)
                        .principal(mockJWTAuth))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
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

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_DECKNAME + "/dummyDeckName")
                .principal(mockJWTAuth))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    void testGetAllMatchupRecords() {
        // Arrange
        MatchupServiceImpl service = new MatchupServiceImpl();
        service.setRepository(mockRepository);

        User newUser = User.builder().email("!!@@123").build();
        Matchup nonUserMatchup = Matchup.builder().playerOneName("Fred").playerTwoName("Jim").createdBy(newUser).playerOneDeck(sampleDeckPikachu)
                .playerTwoDeck(sampleDeckSquirtle).winningDeck(Constants.PIKACHU).format("Standard").notes("none").build();

        List<Matchup> matchups = new ArrayList<>();
        matchups.add(nonUserMatchup);
        matchups.add(sampleMatchup3);
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdOn");

        when(service.getAllMatchups()).thenReturn(matchups);
        when(mockRepository.findByCreatedBy_Email(dummyUser.getEmail(), sort)).thenReturn(matchups);

        // Act
        Map<String, Map<String, String>> allMatchupRecords = service.getAllMatchupRecords(dummyUser.getEmail());

        // Assert
        assertNotNull(allMatchupRecords, "The returned map should not be null");
        assertFalse(allMatchupRecords.isEmpty());
        assertEquals(3, allMatchupRecords.size());
    }

    @Test
    void getMatchupsByPlayerName() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_USERNAME + "/dummyName"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    void getMatchupsByFormat() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup1);

        when(mockService.getAllMatchupsByDeckName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(matchups);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_MATCHUPS_BY_FORMAT + "/dummyName"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(matchups);
    }

    @Test
    public void testGetMatchupPercentagesByDeckName() throws Exception {
        // Arrange
        String deckName = Constants.PIKACHU;
        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put("matchup1", 0.5);
        expectedResult.put("matchup2", 0.75);
        expectedResult.put("matchup3", 1.0);

        when(mockService.getMatchupPercentagesByDeckName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(expectedResult);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_MATCHUP_BY_DECKNAME + "/" + Constants.PIKACHU)
                        .principal(mockJWTAuth))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .equals(expectedResult);
    }

    private List<Matchup> prepareMockMatchups() {
        // Create mock Matchup objects with various deck names
        // Ensure you create Matchup objects with different deck names for thorough testing
        // Example:
        // Matchup matchup1 = new Matchup(new Deck("Deck1"), new Deck("Deck2"), ..., ..., ...);
        // Matchup matchup2 = new Matchup(new Deck("Deck2"), new Deck("Deck3"), ..., ..., ...);
        // ...
        return Arrays.asList(sampleMatchup1, sampleMatchup2, sampleMatchup3);
    }

    private Map<String, String> mockIndividualRecords(String deckName) {
        Map<String, String> records = new HashMap<>();
        records.put("SomeKeyBasedOnLogic", "SomeValue");
        return records;
    }

    @Test
    @DisplayName("Does GetAllMatchups endpoint work?")
    public void testGetAllMatchups() throws Exception {
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup1);
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup3);

        when(mockService.getAllMatchups()).thenReturn(matchups);
        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_GET_ALL_MATCHUPS)
                .principal(mockJWTAuth))
        .andExpect(MockMvcResultMatchers.status().isOk());

        response.equals(matchups);
        response.equals(matchups != null);
    }

    @Test
    @DisplayName("Does GetMatchupPercentagesByDeckName Endpoint work?")
    public void testGetAllMatchupPercentagesByDeckName() throws Exception {
        String deckName = Constants.PIKACHU;
        List<Matchup> matchups = new ArrayList<>();
        matchups.add(sampleMatchup1);
        matchups.add(sampleMatchup2);
        matchups.add(sampleMatchup3);

        Map<String, Double> expectedResponse = new HashMap<>();
        expectedResponse.put("OpponentDeck1", 75.0);
        expectedResponse.put("OpponentDeck2", 50.0);

        when(mockService.getMatchupPercentagesByDeckName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(expectedResponse);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_MATCHUPS_PERCENTAGES_BY_DECKNAME + "/" + deckName)
                        .principal(mockJWTAuth))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response.equals(expectedResponse);
        response.equals(expectedResponse != null);
    }

    @Test
    @DisplayName("Test getTotalMatchesByDeck endpoint")
    public void testGetTotalMatchesByDeck() throws Exception {
        String deckName = "sampleDeckName";
        Map<String, Integer> expectedResponse = new HashMap<>();
        expectedResponse.put("MatchType1", 10);
        expectedResponse.put("MatchType2", 20);
        // ... Populate the map as needed for your test

        when(mockService.getTotalMatchesByDeck(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(expectedResponse);

        ResultActions response = mockMvc.perform(get(MatchupController.MATCHUPS + MatchupController.ENDPOINT_TOTAL_GAMES + "/" + deckName)
                        .principal(mockJWTAuth))
                .andExpect(MockMvcResultMatchers.status().isOk());

        response.equals(expectedResponse);
        response.equals(expectedResponse != null);
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