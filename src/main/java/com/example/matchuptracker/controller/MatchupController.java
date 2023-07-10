package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.service.MatchupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matchups")
@Slf4j
public class MatchupController {

    private final MatchupService service;

    public MatchupController(MatchupService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> mthealth() { return new ResponseEntity<>(HttpStatus.OK); }

    @GetMapping("/getAll")
    public ResponseEntity<List<Matchup>> getAll() {
        return new ResponseEntity<>(service.getAllMatchups(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Matchup> add(@RequestBody Matchup matchup) {
        return new ResponseEntity<>(service.saveMatchup(matchup), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Matchup> updateMatchup(@PathVariable int id, @RequestBody Matchup matchup) {
        log.debug("Successfully updated the following matchup: " + matchup.toString());
        Matchup response = service.updateMatchup(id, matchup);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbymatchid/{id}")
    public Matchup getMatchupById(@PathVariable int id) {
        return service.findMatchupById(id);
    }

    @GetMapping("/deckName/{deckName}")
    public List<Matchup> getMatchupByDeckName(@PathVariable String deckName) {
        return service.getAllMatchupsByDeckName(deckName);
    }

    @GetMapping("/playerName/{playerName}")
    public List<Matchup> getMatchupsByPlayerName(@PathVariable String playerName) {
        return service.getAllMatchupsByPlayerName(playerName);
    }

    @GetMapping("/format/{format}")
    public List<Matchup> getMatchupsByFormat(@PathVariable String format) {
        return service.getAllMatchupsByFormat(format);
    }

    @GetMapping("/percentages/{deckName}")
    public Map<String, Double> getMatchupPercentagesByDeckName(@PathVariable String deckName) {
        return service.getMatchupPercentagesByDeckName(deckName);
    }

    @GetMapping("/totalGames/{deckName}")
    public Map<String, Integer> getTotalMatchesByDeck(@PathVariable String deckName) {
        return service.getTotalMatchesByDeck(deckName);
    }

    @GetMapping("/individual/{deckName}")
    public Map<String, String> getIndividualRecordsByDeck(@PathVariable String deckName) {
        return service.getIndividualRecordsByDeckName(deckName);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMatchups(@PathVariable int id) {
        service.deleteMatchup(id);
    }

}
