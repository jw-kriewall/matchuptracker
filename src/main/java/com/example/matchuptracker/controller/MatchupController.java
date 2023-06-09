package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.service.MatchupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matchups")
public class MatchupController {

    private final MatchupService service;

    public MatchupController(MatchupService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Matchup> getAll() {
        return service.getAllMatchups();
    }

    @PostMapping("/add")
    public String add(@RequestBody Matchup matchup) {
        service.saveMatchup(matchup);
        return "Matchup added successfully!";
    }

    @PutMapping("/update/{id}")
    public String updateMatchup(@PathVariable int id, @RequestBody Matchup matchup) {
        service.updateMatchup(id, matchup);
        return "Successfully updated the following matchup: " + matchup.toString();
    }

    @GetMapping("/getbymatchid/{id}")
    public String getMatchupById(@PathVariable int id) {
        return service.findMatchupById(id).toString();
    }

    @GetMapping("/deckName/{name}")
    public List<Matchup> getMatchupByDeckName(@PathVariable String name) {
        return service.getAllMatchupsByDeckName(name);
    }


    //    TODO    //
    // get by user
    //
}
