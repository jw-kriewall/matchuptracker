package com.example.matchuptracker.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class MatchupRepositoryTest {

//6

//    private final MatchupRepository repository;
//
//    public MatchupRepositoryTest(MatchupRepository repository) {
//        this.repository = repository;
//    }
//
//    @Test
//    public void Repository_SaveAll_ReturnSave() {
//        Matchup matchup = Matchup.builder()
//                .format("Standard")
//                .notes("Some Notes")
//                .playerOneDeck("Pikachu")
//                .playerTwoDeck("Squirtle")
//                .playerOneName("PlayerOne")
//                .playerTwoName("PlayerTwo")
//                .winningDeck("Pikachu")
//                .build();
//
//        Matchup savedMatchup = repository.save(matchup);
//
//        Assertions.assertThat(savedMatchup).isNotNull();
//        Assertions.assertThat(savedMatchup.getId()).isGreaterThan(0);
//    }
}
