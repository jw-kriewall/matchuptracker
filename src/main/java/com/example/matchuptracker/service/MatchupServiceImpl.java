package com.example.matchuptracker.service;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.repository.MatchupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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
    public Matchup saveMatchup(Matchup matchup) {
        repository.save(matchup);
        return matchup;
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
                .forEach(matchupsByPlayer::add);
        return matchupsByPlayer;
    }

    @Override
    public List<Matchup> getAllMatchupsByDeckName(String deckName) {
        return repository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(matchup -> matchup.getPlayerOneDeck().equalsIgnoreCase(deckName) || matchup.getPlayerTwoDeck().equalsIgnoreCase(deckName))
                .collect(Collectors.toList());
    }

    // @TODO: Lookup predicates
    @Override
    public List<Matchup> getAllMatchupsByFormat(String format) {
        List<Matchup> matchupsByFormat = new ArrayList<>();
        repository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(matchup -> matchup.getFormat().toLowerCase().contains(format.toLowerCase()))
                .forEach(matchupsByFormat::add);
        return matchupsByFormat;
    }

    @Override
    public Map<String, Double> getMatchupPercentagesByDeckName(String deckName) {

        Map<String, Double> winningPercentageMap = new HashMap<>();
        List<Matchup> matchupsIncludingDeckName = getAllMatchupsByDeckName(deckName);

        // Make a smaller list of matchups where only one other deck is checked.
        // add that deck to the checkedMatchups array so it doesn't get checked again.

        // matchup 1 = {playerOneDeck: Pikachu, playerTwoDeck: Squirtle, winningDeck: Pikachu}
        // matchup 2 = {playerOneDeck: Pikachu, playerTwoDeck: Charizard, winningDeck: Pikachu}
        // matchup 3 = {playerOneDeck: Squirtle, playerTwoDeck: Pikachu, winningDeck: Squirtle}

        // loop through all matchups to find all where playerTwoDeck or playerOneDeck == some checked deck
        // that isn't in map already.
        // calculate that win percentage
        // add to map.

        for(Matchup matchup : matchupsIncludingDeckName) {

            String checkedOpponentDeck = "";
            long matchupTotalGames = 0;
            long totalWins = 0;

            String deckOne = matchup.getPlayerOneDeck();

            // Pull out deck check logic.

            // Setting deck to filter by
            if(!winningPercentageMap.containsKey(deckOne) &&
                !deckOne.contentEquals(deckName)) {
                checkedOpponentDeck = deckOne;
            } else if (!winningPercentageMap.containsKey(matchup.getPlayerTwoDeck()) &&
                    !matchup.getPlayerTwoDeck().contentEquals(deckName)) {
                checkedOpponentDeck = matchup.getPlayerTwoDeck();
            }

            if(checkedOpponentDeck != ""){
                String finalCheckedOpponentDeck = checkedOpponentDeck;
                matchupTotalGames = matchupsIncludingDeckName.stream().filter(it ->
                        it.getPlayerOneDeck().contains(finalCheckedOpponentDeck)).count() +
                        matchupsIncludingDeckName.stream().filter(it ->
                        it.getPlayerTwoDeck().contains(finalCheckedOpponentDeck)).count();
                totalWins = matchupsIncludingDeckName.stream().filter(it ->
                        it.getWinningDeck().contains(finalCheckedOpponentDeck)).count();

                if(!finalCheckedOpponentDeck.equals(deckName)) {
                    totalWins = matchupTotalGames - totalWins;
                }
                winningPercentageMap.put(finalCheckedOpponentDeck, calculatePercentage(totalWins, matchupTotalGames));
            }
        }
        return winningPercentageMap;
    }

    @Override
    public String getOverallRecordByDeckName(String deckName) {
        return null;
    }

    public Map<String, String> getIndividualRecordsByDeckName(String deckName) {
        // matchup 1 = {playerOneDeck: Pikachu, playerTwoDeck: Squirtle, winningDeck: Pikachu}
        // matchup 2 = {playerOneDeck: Charizard, playerTwoDeck: Pikachu, winningDeck: Pikachu}
        // matchup 3 = {playerOneDeck: Squirtle, playerTwoDeck: Pikachu, winningDeck: Squirtle}

        List<Matchup> matchups = getAllMatchupsByDeckName(deckName);
        Map<String, String> recordMap = new HashMap<>();

        recordMap.put(deckName, getRecordInMirrorMatch(deckName));

        int wins = 0;
        int losses = 0;
        int ties = 0;

        for(Matchup matchup : matchups) {
            if(!recordMap.containsKey(matchup.getPlayerOneDeck())) {
                // I now have a list of matchups with the passed in deckName.
                // iterate through and find if playerOneDeck is already named in matchups list.
                // if not, iterate through matchups to calculate record. && !unrecordedMatchup.getPlayerOneDeck().equals(unrecordedMatchup.getPlayerTwoDeck())
                // reset record before calculating next.
                String checkedDeck = matchup.getPlayerOneDeck();
                for(Matchup unrecordedMatchup : matchups) {
                    if(unrecordedMatchup.getPlayerOneDeck().equals(checkedDeck) || unrecordedMatchup.getPlayerTwoDeck().equals(checkedDeck)) {
                        if(unrecordedMatchup.getWinningDeck().equals(unrecordedMatchup.getPlayerOneDeck()) && unrecordedMatchup.getWinningDeck().equals(unrecordedMatchup.getPlayerTwoDeck())) {
                            ties += 1;
                        } else if(!Objects.equals(unrecordedMatchup.getPlayerOneDeck(), unrecordedMatchup.getWinningDeck()) && !Objects.equals(unrecordedMatchup.getPlayerTwoDeck(), unrecordedMatchup.getWinningDeck())){
                            ties += 1;
                        } else if(unrecordedMatchup.getWinningDeck().equals(deckName)) {
                            wins += 1;
                        } else {
                            losses += 1;
                        }
                    }
                }
                recordMap.put(checkedDeck, calculateRecord(wins, losses, ties));
                wins = 0;
                losses = 0;
                ties = 0;
            } else if (!recordMap.containsKey(matchup.getPlayerTwoDeck())) {
                String checkedDeck = matchup.getPlayerTwoDeck();
                for(Matchup unrecordedMatchup : matchups) {
                    if(unrecordedMatchup.getPlayerOneDeck().equals(checkedDeck) || unrecordedMatchup.getPlayerTwoDeck().equals(checkedDeck)) {
                        if(unrecordedMatchup.getWinningDeck().equals(unrecordedMatchup.getPlayerOneDeck()) && unrecordedMatchup.getWinningDeck().equals(unrecordedMatchup.getPlayerTwoDeck())) {
                            ties += 1;
                        } else if(unrecordedMatchup.getWinningDeck().equals("draw") ||
                                unrecordedMatchup.getWinningDeck().equals("tie") ||
                                unrecordedMatchup.getWinningDeck().equals("N/A") ||
                                unrecordedMatchup.getWinningDeck().equals("none")){
                            ties += 1;
                        } else if(unrecordedMatchup.getWinningDeck().equals(deckName)) {
                            wins += 1;
                        } else {
                            losses += 1;
                        }
                    }
                }
                recordMap.put(checkedDeck, calculateRecord(wins, losses, ties));
                wins = 0;
                losses = 0;
                ties = 0;
            }
        }

        return recordMap;
    }

    @Override
    public String getRecordInMirrorMatch(String deckName) {
        List<Matchup> matchups = getAllMatchupsByDeckName(deckName);

        int wins = 0;
        int losses = 0;
        int ties = 0;

        for(Matchup matchup : matchups) {
            String playerOneDeck = matchup.getPlayerOneDeck();
            String playerTwoDeck = matchup.getPlayerTwoDeck();
            if(playerOneDeck.equals(playerTwoDeck) && (matchup.getWinningDeck() == deckName)) {
                wins += 1;
                losses += 1;
            } else if(playerOneDeck.equals(playerTwoDeck) && (
                    matchup.getWinningDeck().equals("draw") ||
                    matchup.getWinningDeck().equals("tie") ||
                    matchup.getWinningDeck().equals("N/A") ||
                    matchup.getWinningDeck().equals("none"))) {
                ties += 1;
            }
        }
        return calculateRecord(wins, losses, ties);
    }

    public Map<String, Integer> getTotalMatchesByDeck(String deckName) {
        Map<String, Integer> matchupsCount = new HashMap<>();
        matchupsCount.put(deckName, 0);

        List<Matchup> matchupsIncludingDeckName = getAllMatchupsByDeckName(deckName);

        for(Matchup matchup : matchupsIncludingDeckName) {

            String deckOne = matchup.getPlayerOneDeck();
            String deckTwo = matchup.getPlayerTwoDeck();

            if(deckOne.equals(deckTwo)) {
                matchupsCount.merge(deckName, 1, Integer::sum);
            }
            if(!deckOne.equals(deckTwo)) {
                if(!deckOne.contains(deckName)) {
                    matchupsCount.merge(deckOne, 1, Integer::sum);
                } else if (!deckTwo.contains(deckName)) {
                    matchupsCount.merge(deckTwo, 1, Integer::sum);
                }
            }
        }
        return matchupsCount;
    }


    private double calculatePercentage(long numerator, long denominator) {
        return ((double)numerator / (double)denominator) * 100;
    }

    private String calculateRecord(int wins, int losses, int draws) {
        return wins + "-" + losses + "-" + draws;
    }

}
