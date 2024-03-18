package com.example.matchuptracker.service.matchup;

import com.example.matchuptracker.model.Matchup;

import java.util.List;
import java.util.Map;

public interface MatchupService {

    List<Matchup> getAllMatchups();
    Matchup saveMatchup(Matchup matchup);
    Matchup updateMatchup(int id, Matchup matchup);
    Matchup findMatchupById(int id);
    List<Matchup> getAllMatchupsByPlayerName(String name);
    List<Matchup> getAllMatchupsByDeckName(String email, String deckName);
    List<Matchup> getAllMatchupsByFormat(String format);
    List<Matchup> getAllMatchupsByPlayerEmail(String email);
    Map<String, Double> getMatchupPercentagesByDeckName(String email, String deckName);
    String getOverallRecordByDeckName(String deckName);
//    Map<String, String> getIndividualRecordsByDeckName(String deckName);
    Map<String, Integer> getTotalMatchesByDeck(String email, String deckName);
    Map<String, Map<String, String>> getAllMatchupRecords(String email, String format);
    void deleteMatchup(int id, String email);
    Integer countAllByPlayerEmail(String email, List<String> deckNames);
    // @TODO: Get matchups by UserID - modify getAllMatchupsByPlayerName to be by playerID
    List<Matchup> getAllMatchupsByPlayerEmailAndFormat(String email, String format);
}
