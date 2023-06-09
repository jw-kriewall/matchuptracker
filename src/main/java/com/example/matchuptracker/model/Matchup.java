package com.example.matchuptracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matchup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String playerOne;
    private String playerOneDeck;
    private String playerTwo;
    private String playerTwoDeck;
    private String startingPlayer;
    private String winningDeck;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    private String format;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerOneDeck() {
        return playerOneDeck;
    }

    public void setPlayerOneDeck(String playerOneDeck) {
        this.playerOneDeck = playerOneDeck;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getPlayerTwoDeck() {
        return playerTwoDeck;
    }

    public void setPlayerTwoDeck(String playerTwoDeck) {
        this.playerTwoDeck = playerTwoDeck;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public String getWinningDeck() {
        return winningDeck;
    }

    public void setWinningDeck(String winningDeck) {
        this.winningDeck = winningDeck;
    }
}
