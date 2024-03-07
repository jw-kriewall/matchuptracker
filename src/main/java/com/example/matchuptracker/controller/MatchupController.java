package com.example.matchuptracker.controller;

import com.example.matchuptracker.Utils.JwtUtil;
import com.example.matchuptracker.model.Matchup;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.service.matchup.MatchupService;
import com.example.matchuptracker.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.matchuptracker.controller.MatchupController.MATCHUPS;
import static com.example.matchuptracker.controller.MatchupController.API;
import static com.example.matchuptracker.controller.MatchupController.VERSION;

@RestController
@RequestMapping(API + VERSION + MATCHUPS)
@Slf4j
@CrossOrigin("*")
public class MatchupController {

    public static final String API = "/api";
    public static final String VERSION = "/v1";
    public static final String MATCHUPS = "/matchups";

    public static final String ENDPOINT_GET_ALL = "";
    public static final String ENDPOINT_GET_ALL_MATCHUPS = "/admin";
    public static final String ENDPOINT_ADD = "/add";
    public static final String ENDPOINT_UPDATE = "/update";
    public static final String ENDPOINT_GET_MATCHUP_BY_ID = "/getbymatchid";
    public static final String ENDPOINT_GET_MATCHUP_BY_DECKNAME = "/deckName";
    public static final String ENDPOINT_GET_MATCHUP_BY_USERNAME = "/playerName";
    public static final String ENDPOINT_MATCHUPS_BY_FORMAT = "/format";
    public static final String ENDPOINT_MATCHUPS_PERCENTAGES_BY_DECKNAME = "/percentages";
    public static final String ENDPOINT_GET_ALL_MATCHUP_RECORDS = "/records";
    public static final String ENDPOINT_TOTAL_GAMES = "/totalGames";
    public static final String ENDPOINT_COUNT_ALL = "/count";
    public static final String ENDPOINT_DELETE = "/delete";
    private final MatchupService service;
    private final UserService userService;

    public MatchupController(MatchupService service, UserService userService) {
        this.service = service; this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> mthealth() { return new ResponseEntity<>(HttpStatus.OK); }

    @GetMapping(ENDPOINT_GET_ALL_MATCHUPS)
    public ResponseEntity<List<Matchup>> getAll(Authentication authToken) {
        return new ResponseEntity<>(service.getAllMatchups(), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_GET_ALL)
    public ResponseEntity<List<Matchup>> getAllByEmail(Authentication authToken) throws JsonProcessingException {
        return new ResponseEntity<>(service.getAllMatchupsByPlayerEmail(JwtUtil.getEmailFromJWT(authToken)), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_GET_ALL + "/{format}")
    public ResponseEntity<List<Matchup>> getAllByEmailAndFormat(Authentication authToken, @PathVariable("format") String format) throws JsonProcessingException {
        return new ResponseEntity<>(service.getAllMatchupsByPlayerEmailAndFormat(JwtUtil.getEmailFromJWT(authToken), format), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_COUNT_ALL)
    @Description("Takes in a list of deckNames. Returns a count of all matchups for display purposes")
    public ResponseEntity<Integer> countAllByEmail(Authentication authToken, @RequestParam List<String> deckNames) throws JsonProcessingException {
        return new ResponseEntity<>(service.countAllByPlayerEmail(JwtUtil.getEmailFromJWT(authToken), deckNames), HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_ADD)
    public ResponseEntity<Matchup> add(@RequestBody Matchup matchup) {
        String email = matchup.getCreatedBy().getEmail();
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            matchup.setCreatedBy(user);

            Matchup savedMatchup = service.saveMatchup(matchup);

            if (savedMatchup != null) {
                return new ResponseEntity<>(savedMatchup, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        return service.getAllMatchupsByDeckName(JwtUtil.getEmailFromJWT(authToken), deckName);
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
    public Map<String, Double> getMatchupPercentagesByDeckName(@PathVariable String deckName, Authentication authToken) {
        return service.getMatchupPercentagesByDeckName(JwtUtil.getEmailFromJWT(authToken), deckName);
    }

    @GetMapping(ENDPOINT_TOTAL_GAMES + "/{deckName}")
    public Map<String, Integer> getTotalMatchesByDeck(@PathVariable String deckName, Authentication authToken) {
        return service.getTotalMatchesByDeck(JwtUtil.getEmailFromJWT(authToken), deckName);
    }

    @GetMapping(ENDPOINT_GET_ALL_MATCHUP_RECORDS + "/{format}")
    public Map<String, Map<String, String>> getAllMatchupRecords(Authentication authToken, @PathVariable String format) {
        return service.getAllMatchupRecords(JwtUtil.getEmailFromJWT(authToken), format);
    }

    @DeleteMapping(ENDPOINT_DELETE + "/{id}")
    public void deleteMatchups(@PathVariable int id) {
        service.deleteMatchup(id);
    }

}
