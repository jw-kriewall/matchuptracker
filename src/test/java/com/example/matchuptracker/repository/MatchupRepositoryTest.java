package com.example.matchuptracker.repository;

import com.example.matchuptracker.service.MatchupService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class MatchupRepositoryTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void mockMvcCreated() {
//        ServletContext context = webApplicationContext.getServletContext();
//        Assert.assertNotNull(context);
    }

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
