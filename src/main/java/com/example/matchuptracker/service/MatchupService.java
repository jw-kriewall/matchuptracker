package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;

import java.util.List;
import java.util.Map;

public interface MatchupService {

    List<Matchup> getAllMatchups();
    String saveMatchup(Matchup matchup);
    Matchup updateMatchup(int id, Matchup matchup);
    Matchup findMatchupById(int id);
    List<Matchup> getAllMatchupsByPlayerName(String name);
    List<Matchup> getAllMatchupsByDeckName(String deckName);
    List<Matchup> getAllMatchupsByFormat(String format);
    Map<String, Double> getMatchupPercentagesByDeckName(String deckName);
    Map<String, Integer> getTotalMatchesByDeck(String deckName);
}
