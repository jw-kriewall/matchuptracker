package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class MatchupServiceImpl implements MatchupService {
    @Autowired
    private final MatchupRepository repository;

    public MatchupServiceImpl(MatchupRepository repository) {
        this.repository = repository;
    }

    @Override
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

    @Override
    public List<Matchup> getAllMatchupsByFormat(String format) {
        List<Matchup> matchupsByFormat = new ArrayList<>();
        repository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(matchup -> matchup.getFormat().toLowerCase().contains(format.toLowerCase()))
                .forEach(result -> matchupsByFormat.add(result));
        return matchupsByFormat;
    }

    @Override
    public Map<String, Double> getMatchupPercentagesByDeckName(String deckName) {
        // filter through DB for all where playerOneDeck or playerTwoDeck .contains( deckName )
        // Count # of matchs + wins for the given deck name
        // generate overall winning percentage


        Map<String, Double> winningPercentageMap = new HashMap<>();

        List<Matchup> matchupsIncludingDeckName = getAllMatchupsByDeckName(deckName);
        int numberOfTotalMatchups = (int)matchupsIncludingDeckName.stream().count();

        // Make a smaller list of matchups where only one other deck is checked.
        // add that deck to the checkedMatchups array so it doesn't get checked again.

        // matchup 1 = {playerOneDeck: Pikachu, playerTwoDeck: Squirtle, winningDeck: Pikachu}
        // matchup 2 = {playerOneDeck: Pikachu, playerTwoDeck: Charizard, winningDeck: Pikachu}
        // matchup 3 = {playerOneDeck: Squirtle, playerTwoDeck: Pikachu, winningDeck: Squirtle}

        // loop through all matchups to find all where playerTwoDeck or playerOneDeck == some checked deck
        // that isn't in map already.
        // calculate that win percentage
        // add to map.
        for(int i = 0; i < numberOfTotalMatchups; i++) {
            String checkedOpponentDeck = "";
            int matchupTotalGames = 0;
            int totalWins = 0;

            // Setting deck to filter by
            if(!winningPercentageMap.containsKey(matchupsIncludingDeckName.get(i).getPlayerOneDeck()) &&
                !matchupsIncludingDeckName.get(i).getPlayerOneDeck().contentEquals(deckName)) {
                checkedOpponentDeck = matchupsIncludingDeckName.get(i).getPlayerOneDeck();
            } else if (!winningPercentageMap.containsKey(matchupsIncludingDeckName.get(i).getPlayerTwoDeck()) &&
                    !matchupsIncludingDeckName.get(i).getPlayerTwoDeck().contentEquals(deckName)) {
                checkedOpponentDeck = matchupsIncludingDeckName.get(i).getPlayerTwoDeck();
            }
// move
            if(checkedOpponentDeck != ""){
                String finalCheckedOpponentDeck = checkedOpponentDeck;
                matchupTotalGames = (int)matchupsIncludingDeckName.stream().filter(matchup ->
                        matchup.getPlayerOneDeck().contains(finalCheckedOpponentDeck)).count() +
                        (int)matchupsIncludingDeckName.stream().filter(matchup ->
                        matchup.getPlayerTwoDeck().contains(finalCheckedOpponentDeck)).count();
                totalWins = (int)matchupsIncludingDeckName.stream().filter(matchup ->
                        matchup.getWinningDeck().contains(finalCheckedOpponentDeck)).count();


                if(!finalCheckedOpponentDeck.equals(deckName)) {
                    totalWins = matchupTotalGames - totalWins;
                }

                winningPercentageMap.put(finalCheckedOpponentDeck, calculatePercentage(totalWins, matchupTotalGames));
            }
        }

        return winningPercentageMap;
    }


    public double calculatePercentage(int numerator, int denominator){
        double result = ((double)numerator / (double)denominator) * 100;
        if(result > 0){
            return result;
        } else return 0;
    }

}
