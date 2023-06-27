package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchupServiceTest {

    @Mock
    MatchupRepository mockRepository;

    @InjectMocks
    MatchupServiceImpl mockService;

    @Test
    public void testRepository() {
        Assertions.assertNotNull(mockRepository);
    }

    @Test
    public void testGetAllMatchupsByPlayerName() {

        String nameToCheck = "Bob";
        String dummyName = "dummy";

        List<Matchup> dummyData = new ArrayList<>();

        Matchup dummyMatchup1 = Matchup.builder()
                .playerOneName(nameToCheck)
                .playerTwoName(dummyName)
                .build();

        Matchup dummyMatchup2 = Matchup.builder()
                .playerOneName(dummyName)
                .playerTwoName(nameToCheck)
                .build();

        dummyData.add(dummyMatchup1);
        dummyData.add(dummyMatchup2);

        when(mockRepository.findAll()).thenReturn(dummyData);

        Assertions.assertNotNull(mockService.getAllMatchupsByPlayerName(nameToCheck));
        Assertions.assertEquals(mockService.getAllMatchupsByPlayerName(nameToCheck).size(), 2);
    }


}
