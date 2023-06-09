package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class MatchupServiceImpl implements MatchupService {
    @Autowired
    private final MatchupRepository repository;

    public MatchupServiceImpl(MatchupRepository repository) {
        this.repository = repository;
    }

    public List<Matchup> getAllMatchups() {
        return repository.findAll();
    }

    @Override
    public String saveMatchup(Matchup matchup) {
        repository.save(matchup);
        return null;
    }

    @Override
    public Matchup updateMatchup(int id, Matchup matchup) {
        Matchup existingMatchup = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Matchup not found in the database"));

        existingMatchup.setFormat(matchup.getFormat());
        existingMatchup.setNotes(matchup.getNotes());
        existingMatchup.setPlayerOne(matchup.getPlayerOne());
        existingMatchup.setPlayerTwo(matchup.getPlayerTwo());
        existingMatchup.setPlayerOneDeck(matchup.getPlayerOneDeck());
        existingMatchup.setPlayerTwoDeck(matchup.getPlayerTwoDeck());
        existingMatchup.setStartingPlayer(matchup.getStartingPlayer());
        existingMatchup.setWinningDeck(matchup.getWinningDeck());

        repository.save(existingMatchup);
        return existingMatchup;
    }

    @Override
    public Matchup findMatchupById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Matchup not found in the database"));
    }

    @Override
    public List<Matchup> getAllMatchupsByDeckName(String name) {
        List<Matchup> matchupsByDeck = new ArrayList<>();
        repository.findAll().stream().filter(matchup -> (matchup.getPlayerOneDeck().toLowerCase().contains(name.toLowerCase()))).forEach(result -> matchupsByDeck.add(result));
        return matchupsByDeck;
    }

//    @Override
//    public List<Matchup> getMatchupsByPlayerName(String name) {
//        return repository.findById(name.getBytes(StandardCharsets.UTF_8));
//    }
}
