package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;

import java.util.List;
import java.util.Map;

public interface MatchupService {

    List<Matchup> getAllMatchups();
    Matchup saveMatchup(Matchup matchup);
    Matchup updateMatchup(int id, Matchup matchup);
    Matchup findMatchupById(int id);
    List<Matchup> getAllMatchupsByPlayerName(String name);
    List<Matchup> getAllMatchupsByDeckName(String deckName);
    List<Matchup> getAllMatchupsByFormat(String format);
    List<Matchup> getAllMatchupsByPlayerEmail(String email);
    Map<String, Double> getMatchupPercentagesByDeckName(String deckName);
    String getOverallRecordByDeckName(String deckName);
    Map<String, String> getIndividualRecordsByDeckName(String deckName);
    Map<String, Integer> getTotalMatchesByDeck(String deckName);
    Map<String, Map<String, String>> getAllMatchupRecords();
    void deleteMatchup(int id);
    // @TODO: Get matchups by UserID - modify getAllMatchupsByPlayerName to be by playerID
}
