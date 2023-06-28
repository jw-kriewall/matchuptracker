package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

// https://www.toptal.com/java/a-guide-to-everyday-mockito

@ExtendWith(MockitoExtension.class)
public class MatchupServiceTest {

    @Mock
    MatchupRepository mockRepository;

    @InjectMocks
    MatchupServiceImpl mockService;

    String dummyName = "dummy";
    String pikachu = "Pikachu";
    String squirtle = "Squirtle";
    String charizard = "Charizard";
    String bulbasaur = "Bulbasaur";

    String nameToCheck = "Bob";
    String format = "Standard";

        Matchup dummyMatchup1 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(pikachu)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck(pikachu)
                .build();

        Matchup dummyMatchup2 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(squirtle)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck(squirtle)
                .build();

        Matchup dummyMatchup3 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck(pikachu)
                .build();

        Matchup dummyMatchup4 = Matchup.builder()
                .playerOneName(dummyName)
                .playerTwoName(nameToCheck)
                .playerOneDeck(charizard)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck(squirtle)
                .build();

        Matchup dummyMatchup5 = Matchup.builder()
                .playerOneName(dummyName)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(squirtle)
                .format(dummyName)
                .winningDeck(squirtle)
                .build();

        List<Matchup>dummyData = new ArrayList<>(List.of(dummyMatchup1, dummyMatchup2, dummyMatchup3, dummyMatchup4));


    @Test
    public void testRepository() {
        Assertions.assertNotNull(mockRepository);
    }

    @Test
    public void getAllMatchups() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchups());
        Assertions.assertEquals(mockService.getAllMatchups().size(), dummyData.size());
        Assertions.assertEquals(mockService.getAllMatchups(), dummyData);
    }

    @Test
    public void testGetAllMatchupsByPlayerName() {
        // Arrange, Act, Assert
        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByPlayerName(nameToCheck));
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(nameToCheck).size(), dummyData.size());
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(nameToCheck), dummyData);
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(dummyName), dummyData);
    }

    @Test
    public void testGetAllMatchupsByDeckName() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByDeckName(pikachu));
        Assertions.assertEquals(mockService.getAllMatchupsByDeckName(pikachu).size(), 3);
        Assertions.assertEquals(mockService.getAllMatchupsByDeckName(dummyName).size(), 0);
    }

    @Test
    public void testGetAllMatchupsByFormat() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByFormat(format));
        Assertions.assertEquals(mockService.getAllMatchupsByFormat(format).size(), dummyData.size());
        Assertions.assertEquals(mockService.getAllMatchupsByFormat(format), dummyData);
    }

    @Test
    public void testGetMatchupPercentagesByDeckName() {

        Map<String, Double> dummyMatchupsCharizard = new HashMap<>();
        dummyMatchupsCharizard.put(pikachu, 0.0);
        dummyMatchupsCharizard.put(squirtle, 0.0);

        Map<String, Double> dummyMatchupsPikachu = new HashMap<>();
        dummyMatchupsPikachu.put(squirtle, 50.0);
        dummyMatchupsPikachu.put(charizard, 100.0);

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getMatchupPercentagesByDeckName(charizard));
        Assertions.assertNotNull(mockService.getMatchupPercentagesByDeckName(pikachu));
        Assertions.assertNotNull(mockService.getAllMatchupsByDeckName(squirtle));

        Assertions.assertEquals(mockService.getMatchupPercentagesByDeckName(charizard), dummyMatchupsCharizard);
    }

    @Test
    public void testGetTotalMatchesByDeck() {

        when(mockRepository.findAll()).thenReturn(dummyData);

        Map<String, Integer> dummyMatchupsPikachu = new HashMap<>();
        dummyMatchupsPikachu.put(pikachu, 0);
        dummyMatchupsPikachu.put(squirtle, 2);
        dummyMatchupsPikachu.put(charizard, 1);

        Map<String, Integer> dummyMatchupsCharizard = new HashMap<>();
        dummyMatchupsCharizard.put(charizard, 0);
        dummyMatchupsCharizard.put(squirtle, 1);
        dummyMatchupsCharizard.put(pikachu, 1);

        Assertions.assertNotNull(dummyMatchupsPikachu);
        Assertions.assertNotNull(dummyMatchupsCharizard);
        Assertions.assertEquals(mockService.getTotalMatchesByDeck(charizard), dummyMatchupsCharizard);
        Assertions.assertEquals(mockService.getTotalMatchesByDeck(pikachu), dummyMatchupsPikachu);
    }

    @Test
    public void testSaveMatchup() {
        // Act
        when(mockRepository.save(Mockito.any(Matchup.class))).thenReturn(dummyMatchup1);
        Matchup savedMatchup = mockService.saveMatchup(dummyMatchup1);
        // Assert
        Assertions.assertNotNull(savedMatchup);
    }

    @Test
    public void testUpdateMatchup() {

        when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyMatchup1));

        Matchup matchup = mockService.updateMatchup(1, dummyMatchup2);

        Assertions.assertNotNull(matchup);
        Assertions.assertEquals(matchup.toString(), dummyMatchup2.toString());
//        Assertions.assertNotEquals(matchup.toString(), dummyMatchup1.toString());
    }

    @Test
    public void testFindMatchupsById() {
        when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyMatchup3));

        Assertions.assertNotNull(mockService.findMatchupById(1));
        Assertions.assertEquals(mockService.findMatchupById(1).toString(), dummyMatchup3.toString());
    }

    @Test
    public void testGetIndividualRecordsByDeckName() {
        Matchup charizardMatchup1 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(charizard)
                .format(format)
                .winningDeck(charizard)
                .build();
        Matchup charizardMatchup2 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck(charizard)
                .build();
        Matchup charizardMatchup3 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(squirtle)
                .playerTwoDeck(charizard)
                .format(format)
                .winningDeck("tie")
                .build();
        Matchup charizardMatchup4 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck(squirtle)
                .build();
        Matchup charizardMatchup5 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck(charizard)
                .build();
        Matchup charizardMatchup6 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(charizard)
                .format(format)
                .winningDeck("none")
                .build();
        Matchup charizardMatchup7 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(bulbasaur)
                .format(format)
                .winningDeck(bulbasaur)
                .build();
        Matchup charizardMatchup8 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(charizard)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck(charizard)
                .build();

        List<Matchup>charizardDummyData = new ArrayList<>(List.of(
                charizardMatchup1, charizardMatchup2, charizardMatchup3, charizardMatchup4, charizardMatchup5, charizardMatchup6, charizardMatchup7, charizardMatchup8));

        Map<String, String> expectedRecords = new HashMap<>();
        expectedRecords.put(charizard, "1-1-1");
        expectedRecords.put(pikachu, "2-0-0");
        expectedRecords.put(squirtle, "1-1-1");
        expectedRecords.put(bulbasaur, "0-1-0");

        when(mockRepository.findAll()).thenReturn(charizardDummyData);

        Assertions.assertNotNull(mockService.getIndividualRecordsByDeckName(charizard));
        Assertions.assertEquals(expectedRecords, mockService.getIndividualRecordsByDeckName(charizard));
    }

    @Test
    public void testGetRecordInMirrorMatch() {
        Matchup pikachuMatchup1 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(pikachu)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck(pikachu)
                .build();
        Matchup pikachuMatchup2 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(squirtle)
                .playerTwoDeck(squirtle)
                .format(format)
                .winningDeck("N/A")
                .build();
        Matchup pikachuMatchup3 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(pikachu)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck("tie")
                .build();
        Matchup pikachuMatchup4 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(pikachu)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck(pikachu)
                .build();
        Matchup pikachuMatchup5 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .playerOneDeck(pikachu)
                .playerTwoDeck(pikachu)
                .format(format)
                .winningDeck("none")
                .build();

        List<Matchup>pikachuDummyData = new ArrayList<>(List.of(pikachuMatchup1, pikachuMatchup2, pikachuMatchup3, pikachuMatchup4, pikachuMatchup5));

        String expectedRecord = "1-1-2";

        when(mockRepository.findAll()).thenReturn(pikachuDummyData);

        Assertions.assertNotNull(mockService.getRecordInMirrorMatch(pikachu));
        Assertions.assertEquals(expectedRecord, mockService.getRecordInMirrorMatch(pikachu));
    }

}
