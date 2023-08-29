package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Deck;
import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.mockito.Mockito.when;
import static utils.Constants.*;

// https://www.toptal.com/java/a-guide-to-everyday-mockito

@ExtendWith(MockitoExtension.class)
public class MatchupServiceTest {
    // Ctrl + Shift + f10

    @Mock
    MatchupRepository mockRepository;

    @InjectMocks
    MatchupServiceImpl mockService;
    private Deck sampleDeckPikachu = Deck.builder().name(PIKACHU).cards("Cards").build();
    private Deck sampleDeckSquirtle = Deck.builder().name(SQUIRTLE).cards("Cards").build();
    private Deck sampleDeckCharizard = Deck.builder().name(CHARIZARD).cards("Cards").build();
    private Deck sampleDeckBulbasaur = Deck.builder().name(BULBASAUR).cards("Cards").build();
    private Deck sampleDeckPidgey = Deck.builder().name(PIDGEY).cards("Cards").build();

    Date date = new Date();

    Matchup dummyMatchup1 = Matchup.builder()
            .playerOneName(NAME_TO_CHECK)
            .playerTwoName(DUMMY_NAME)
            .playerOneDeck(sampleDeckPikachu)
            .playerTwoDeck(sampleDeckSquirtle)
            .format(FORMAT)
            .winningDeck(PIKACHU)
            .createdOn(date)
            .build();

    Matchup dummyMatchup2 = Matchup.builder()
            .playerOneName(NAME_TO_CHECK)
            .playerTwoName(DUMMY_NAME)
            .playerOneDeck(sampleDeckSquirtle)
            .playerTwoDeck(sampleDeckPikachu)
            .format(FORMAT)
            .winningDeck(SQUIRTLE)
            .createdOn(date)
            .build();

    Matchup dummyMatchup3 = Matchup.builder()
            .playerOneName(NAME_TO_CHECK)
            .playerTwoName(DUMMY_NAME)
            .playerOneDeck(sampleDeckCharizard)
            .playerTwoDeck(sampleDeckPikachu)
            .format(FORMAT)
            .winningDeck(PIKACHU)
            .createdOn(date)
            .build();

    Matchup dummyMatchup4 = Matchup.builder()
            .playerOneName(DUMMY_NAME)
            .playerTwoName(NAME_TO_CHECK)
            .playerOneDeck(sampleDeckCharizard)
            .playerTwoDeck(sampleDeckSquirtle)
            .format(FORMAT)
            .winningDeck(SQUIRTLE)
            .createdOn(date)
            .build();

    Matchup dummyMatchup5 = Matchup.builder()
            .playerOneName(NAME_TO_CHECK)
            .playerTwoName(DUMMY_NAME)
            .playerOneDeck(sampleDeckCharizard)
            .playerTwoDeck(sampleDeckCharizard)
            .format(FORMAT)
            .winningDeck(CHARIZARD)
            .createdOn(date)
            .build();

    List<Matchup> dummyData = new ArrayList<>(List.of(dummyMatchup1, dummyMatchup2, dummyMatchup3, dummyMatchup4, dummyMatchup5));


    @Test
    public void testRepository() {
        Assertions.assertNotNull(mockRepository);
    }

    @Test
    @DisplayName("Does getAll return all matchups?")
    public void getAllMatchups() {

        when(mockRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"))).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchups());
        Assertions.assertEquals(dummyData.size(), mockService.getAllMatchups().size());
        Assertions.assertEquals(dummyData, mockService.getAllMatchups());
    }

    @Test
    @DisplayName("Does getAllMatchups by Player Name work correctly?")
    public void testGetAllMatchupsByPlayerName() {
        // Arrange, Act, Assert
        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByPlayerName(NAME_TO_CHECK));
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(NAME_TO_CHECK).size(), dummyData.size());
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(NAME_TO_CHECK), dummyData);
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(DUMMY_NAME), dummyData);
    }

    @Test
    @DisplayName("Does getAllMatchups by Deck Name work correctly?")
    public void testGetAllMatchupsByDeckName() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByDeckName(PIKACHU));
        Assertions.assertEquals(mockService.getAllMatchupsByDeckName(PIKACHU).size(), 3);
        Assertions.assertEquals(mockService.getAllMatchupsByDeckName(DUMMY_NAME).size(), 0);
    }

    @Test
    @DisplayName("Does getAllMatchups by Format work correctly?")
    public void testGetAllMatchupsByFormat() {

        when(mockRepository.findByFormat(FORMAT)).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByFormat(FORMAT));
        Assertions.assertEquals(mockService.getAllMatchupsByFormat(FORMAT).size(), dummyData.size());
        Assertions.assertEquals(mockService.getAllMatchupsByFormat(FORMAT), dummyData);
    }

    @Test
    @DisplayName("Does getMatchupPercentages by Deck Name work correctly?")
    public void testGetMatchupPercentagesByDeckName() {

        Map<String, Double> dummyMatchupsCharizard = new HashMap<>();
        dummyMatchupsCharizard.put(PIKACHU, 0.0);
        dummyMatchupsCharizard.put(SQUIRTLE, 0.0);

        Map<String, Double> dummyMatchupsPikachu = new HashMap<>();
        dummyMatchupsPikachu.put(SQUIRTLE, 50.0);
        dummyMatchupsPikachu.put(CHARIZARD, 100.0);

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getMatchupPercentagesByDeckName(CHARIZARD));
        Assertions.assertNotNull(mockService.getMatchupPercentagesByDeckName(PIKACHU));
        Assertions.assertNotNull(mockService.getAllMatchupsByDeckName(SQUIRTLE));

        Assertions.assertEquals(mockService.getMatchupPercentagesByDeckName(CHARIZARD), dummyMatchupsCharizard);
    }

    @Test
    @DisplayName("Does getTotalMatches by Deck work correctly?")
    public void testGetTotalMatchesByDeck() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Map<String, Integer> dummyMatchupsPikachu = new HashMap<>();
        dummyMatchupsPikachu.put(PIKACHU, 0);
        dummyMatchupsPikachu.put(SQUIRTLE, 2);
        dummyMatchupsPikachu.put(CHARIZARD, 1);

        Map<String, Integer> dummyMatchupsCharizard = new HashMap<>();
        dummyMatchupsCharizard.put(CHARIZARD, 1);
        dummyMatchupsCharizard.put(SQUIRTLE, 1);
        dummyMatchupsCharizard.put(PIKACHU, 1);

        Assertions.assertNotNull(dummyMatchupsPikachu);
        Assertions.assertNotNull(dummyMatchupsCharizard);
        Assertions.assertEquals(mockService.getTotalMatchesByDeck(CHARIZARD), dummyMatchupsCharizard);
        Assertions.assertEquals(mockService.getTotalMatchesByDeck(PIKACHU), dummyMatchupsPikachu);
    }

    @Test
    @DisplayName("Does Save work correctly?")
    public void testSaveMatchup() {
        // Act
        when(mockRepository.save(Mockito.any(Matchup.class))).thenReturn(dummyMatchup1);
        Matchup savedMatchup = mockService.saveMatchup(dummyMatchup1);
        // Assert
        Assertions.assertNotNull(savedMatchup);
    }

    @Test
    @DisplayName("Does Update work correctly?")
    public void testUpdateMatchup() {

        when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyMatchup1));

        Matchup matchup = mockService.updateMatchup(1, dummyMatchup2);

        Assertions.assertNotNull(matchup);
        Assertions.assertEquals(matchup.toString(), dummyMatchup2.toString());
//        Assertions.assertNotEquals(matchup.toString(), dummyMatchup1.toString());
    }

    @Test
    @DisplayName("Does Delete work correctly?")
    public void testDeleteMatchup() {
        Assertions.assertAll(() -> mockService.deleteMatchup(1));
    }

    @Test
    @DisplayName("Does findMatchupsByID work correctly?")
    public void testFindMatchupsById() {
        when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyMatchup3));

        Assertions.assertNotNull(mockService.findMatchupById(1));
        Assertions.assertEquals(mockService.findMatchupById(1).toString(), dummyMatchup3.toString());
    }

    @Test
    @DisplayName("Does getIndividualRecords by DeckName work correctly?")
    public void testGetIndividualRecordsByDeckName() {
        Matchup charizardMatchup1 = getMatchup(sampleDeckCharizard, sampleDeckCharizard, CHARIZARD);
        Matchup charizardMatchup2 = getMatchup(sampleDeckPikachu, sampleDeckCharizard, PIKACHU);
        Matchup charizardMatchup3 = getMatchup(sampleDeckSquirtle, sampleDeckCharizard, "tie");
        Matchup charizardMatchup4 = getMatchup(sampleDeckCharizard, sampleDeckSquirtle, SQUIRTLE);
        Matchup charizardMatchup5 = getMatchup(sampleDeckSquirtle, sampleDeckCharizard, SQUIRTLE);
        Matchup charizardMatchup6 = getMatchup(sampleDeckCharizard, sampleDeckCharizard, "none");
        Matchup charizardMatchup7 = getMatchup(sampleDeckCharizard, sampleDeckBulbasaur, BULBASAUR);
        Matchup charizardMatchup8 = getMatchup(sampleDeckCharizard, sampleDeckPikachu, CHARIZARD);
        Matchup charizardMatchup9 = getMatchup(sampleDeckCharizard, sampleDeckPidgey, CHARIZARD);

        List<Matchup> charizardDummyData = new ArrayList<>(List.of(
                charizardMatchup1, charizardMatchup2, charizardMatchup3, charizardMatchup4, charizardMatchup5, charizardMatchup6, charizardMatchup7, charizardMatchup8, charizardMatchup9));

        Map<String, String> expectedRecords = new HashMap<>();
        expectedRecords.put(CHARIZARD, "1-1-1");
        expectedRecords.put(PIKACHU, "1-1-0");
        expectedRecords.put(SQUIRTLE, "0-2-1");
        expectedRecords.put(BULBASAUR, "0-1-0");
        expectedRecords.put(PIDGEY, "1-0-0");

        when(mockRepository.findAll()).thenReturn(charizardDummyData);

        Assertions.assertNotNull(mockService.getIndividualRecordsByDeckName(CHARIZARD));
        Assertions.assertEquals(expectedRecords, mockService.getIndividualRecordsByDeckName(CHARIZARD));
    }

    private Matchup getMatchup(Deck playerOneDeck, Deck playerTwoDeck, String winningDeck) {
        Matchup charizardMatchup8 = Matchup.builder()
                .playerOneDeck(playerOneDeck)
                .playerTwoDeck(playerTwoDeck)
                .winningDeck(winningDeck)
                .build();
        return charizardMatchup8;
    }


//    private void testGetRecordInMirrorMatch() {
//        Matchup pikachuMatchup1 = Matchup.builder()
//                .playerOneName(NAME_TO_CHECK)
//                .playerTwoName(DUMMY_NAME)
//                .playerOneDeck(PIKACHU)
//                .playerTwoDeck(SQUIRTLE)
//                .format(FORMAT)
//                .winningDeck(PIKACHU)
//                .build();
//        Matchup pikachuMatchup2 = Matchup.builder()
//                .playerOneName(NAME_TO_CHECK)
//                .playerTwoName(DUMMY_NAME)
//                .playerOneDeck(SQUIRTLE)
//                .playerTwoDeck(SQUIRTLE)
//                .format(FORMAT)
//                .winningDeck("N/A")
//                .build();
//        Matchup pikachuMatchup3 = Matchup.builder()
//                .playerOneName(NAME_TO_CHECK)
//                .playerTwoName(DUMMY_NAME)
//                .playerOneDeck(PIKACHU)
//                .playerTwoDeck(PIKACHU)
//                .format(FORMAT)
//                .winningDeck("tie")
//                .build();
//        Matchup pikachuMatchup4 = Matchup.builder()
//                .playerOneName(NAME_TO_CHECK)
//                .playerTwoName(DUMMY_NAME)
//                .playerOneDeck(PIKACHU)
//                .playerTwoDeck(PIKACHU)
//                .format(FORMAT)
//                .winningDeck(PIKACHU)
//                .build();
//        Matchup pikachuMatchup5 = Matchup.builder()
//                .playerOneName(NAME_TO_CHECK)
//                .playerTwoName(DUMMY_NAME)
//                .playerOneDeck(PIKACHU)
//                .playerTwoDeck(PIKACHU)
//                .format(FORMAT)
//                .winningDeck("none")
//                .build();
//
//        List<Matchup>pikachuDummyData = new ArrayList<>(List.of(pikachuMatchup1, pikachuMatchup2, pikachuMatchup3, pikachuMatchup4, pikachuMatchup5));
//
//        String expectedRecord = "1-1-2";
//
//        when(mockRepository.findAll()).thenReturn(pikachuDummyData);
//
//        Assertions.assertNotNull(mockService.getRecordInMirrorMatch(PIKACHU));
//        Assertions.assertEquals(expectedRecord, mockService.getRecordInMirrorMatch(PIKACHU));
//    }

}
