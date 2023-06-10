package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;

import java.util.List;

public interface MatchupService {

    List<Matchup> getAllMatchups();
    String saveMatchup(Matchup matchup);
    Matchup updateMatchup(int id, Matchup matchup);
    Matchup findMatchupById(int id);
    List<Matchup> getAllMatchupsByPlayerName(String name);
    List<Matchup> getAllMatchupsByDeckName(String deckName);
}
