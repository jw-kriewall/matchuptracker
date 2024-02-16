package com.example.matchuptracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.matchuptracker.controller.DeckDisplayController.API;
import static com.example.matchuptracker.controller.DeckDisplayController.VERSION;
import static com.example.matchuptracker.controller.DeckDisplayController.DECK_DISPLAY;

@RestController
@RequestMapping(API + VERSION + DECK_DISPLAY)
@Slf4j
@CrossOrigin("*")
public class DeckDisplayController {

    public static final String API = "/api";
    public static final String VERSION = "/v1";
    public static final String DECK_DISPLAY = "deckdisplay";



    // For individual users to track their deck names (for drop downs in front end.)
    // There is a list of hardcoded values, but also a list of editable fields so a user can create their own decks.
    // Must match the DeckDisplay object in front end.
    // User should be able to Add and Delete options
    // User can only have 50 options
    // User cannot add a deck they already have in the database
    // Must be associated with their email
}
