package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.service.MatchupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.matchuptracker.controller.MatchupController.MATCHUPS;

@RestController
@RequestMapping(MATCHUPS)
@Slf4j
//@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
@CrossOrigin("*")
public class MatchupController {

    public static final String MATCHUPS = "/matchups";
    public static final String ENDPOINT_GET_ALL = "/getAll";
    public static final String ENDPOINT_GET_ALL_MATCHUPS = "/getAllMatchups";
    public static final String ENDPOINT_ADD = "/add";
    public static final String ENDPOINT_UPDATE = "/update";
    public static final String ENDPOINT_GET_MATCHUP_BY_ID = "/getbymatchid";
    public static final String ENDPOINT_GET_MATCHUP_BY_DECKNAME = "/deckName";
    public static final String ENDPOINT_GET_MATCHUP_BY_USERNAME = "/playerName";
    public static final String ENDPOINT_MATCHUPS_BY_FORMAT = "/format";
    public static final String ENDPOINT_MATCHUPS_PERCENTAGES_BY_DECKNAME = "/percentages";
    public static final String ENDPOINT_MATCHUPS_INDIVIDUAL_RECORDS = "/individual";
    public static final String ENDPOINT_DELETE = "/delete";
    private final MatchupService service;

    public MatchupController(MatchupService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> mthealth() { return new ResponseEntity<>(HttpStatus.OK); }

    @GetMapping(ENDPOINT_GET_ALL_MATCHUPS)
    public ResponseEntity<List<Matchup>> getAll(Authentication authToken) {
        return new ResponseEntity<>(service.getAllMatchups(), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_GET_ALL)
    public ResponseEntity<List<Matchup>> getAllByEmail(Authentication authToken) throws JsonProcessingException {
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authToken;
        String email = jwtAuthentication.getTokenAttributes().get("email").toString();
        return new ResponseEntity<>(service.getAllMatchupsByPlayerEmail(email), HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_ADD)
    public ResponseEntity<Matchup> add(@RequestBody Matchup matchup) {
        return new ResponseEntity<>(service.saveMatchup(matchup), HttpStatus.OK);
    }

    @PutMapping(ENDPOINT_UPDATE + "/{id}")
    public ResponseEntity<Matchup> updateMatchup(@PathVariable int id, @RequestBody Matchup matchup) {
        log.debug("Successfully updated the following matchup: " + matchup.toString());
        Matchup response = service.updateMatchup(id, matchup);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_GET_MATCHUP_BY_ID + "/{id}")
    public Matchup getMatchupById(@PathVariable int id) {
        return service.findMatchupById(id);
    }

    @GetMapping(ENDPOINT_GET_MATCHUP_BY_DECKNAME + "/{deckName}")
    public List<Matchup> getMatchupByDeckName(@PathVariable String deckName, Authentication authToken) {
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authToken;
        String email = jwtAuthentication.getTokenAttributes().get("email").toString();
        return service.getAllMatchupsByDeckName(deckName);
    }

    @GetMapping(ENDPOINT_GET_MATCHUP_BY_USERNAME + "/{playerName}")
    public List<Matchup> getMatchupsByPlayerName(@PathVariable String playerName) {
        return service.getAllMatchupsByPlayerName(playerName);
    }

    @GetMapping(ENDPOINT_MATCHUPS_BY_FORMAT + "/{format}")
    public List<Matchup> getMatchupsByFormat(@PathVariable String format) {
        return service.getAllMatchupsByFormat(format);
    }

    @GetMapping(ENDPOINT_MATCHUPS_PERCENTAGES_BY_DECKNAME + "/{deckName}")
    public Map<String, Double> getMatchupPercentagesByDeckName(@PathVariable String deckName) {
        return service.getMatchupPercentagesByDeckName(deckName);
    }

    @GetMapping("/totalGames/{deckName}")
    public Map<String, Integer> getTotalMatchesByDeck(@PathVariable String deckName) {
        return service.getTotalMatchesByDeck(deckName);
    }

    @GetMapping(ENDPOINT_MATCHUPS_INDIVIDUAL_RECORDS + "/{deckName}")
    public Map<String, String> getIndividualRecordsByDeck(@PathVariable String deckName) {
        return service.getIndividualRecordsByDeckName(deckName);
    }

    @DeleteMapping(ENDPOINT_DELETE + "/{id}")
    public void deleteMatchups(@PathVariable int id) {
        service.deleteMatchup(id);
    }

}
