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
import java.util.Objects;


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
        existingMatchup.setPlayerOneName(matchup.getPlayerOneName());
        existingMatchup.setPlayerTwoName(matchup.getPlayerTwoName());
        existingMatchup.setPlayerOneDeck(matchup.getPlayerOneDeck());
        existingMatchup.setPlayerTwoDeck(matchup.getPlayerTwoDeck());
        existingMatchup.setStartingPlayer(matchup.getStartingPlayer());
        existingMatchup.setWinningDeck(matchup.getWinningDeck());
        existingMatchup.setFormat(matchup.getFormat());

        repository.save(existingMatchup);
        return existingMatchup;
    }

    @Override
    public Matchup findMatchupById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Matchup not found in the database"));
    }

    @Override
    public List<Matchup> getAllMatchupsByPlayerName(String name) {
        List<Matchup> matchupsByPlayer = new ArrayList<>();
        repository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(matchup -> (
                matchup.getPlayerOneName().toLowerCase().contains(name.toLowerCase()) || matchup.getPlayerTwoName().toLowerCase().contains(name.toLowerCase())))
                .forEach(result -> matchupsByPlayer.add(result));
        return matchupsByPlayer;
    }

    @Override
    public List<Matchup> getAllMatchupsByDeckName(String deckName) {
        List<Matchup> matchupsByDeck = new ArrayList<>();
        repository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(matchup -> (
                matchup.getPlayerOneDeck().toLowerCase().contains(deckName.toLowerCase()) || matchup.getPlayerTwoDeck().toLowerCase().contains(deckName.toLowerCase())))
                .forEach(result -> matchupsByDeck.add(result));
        return matchupsByDeck;
    }


}
