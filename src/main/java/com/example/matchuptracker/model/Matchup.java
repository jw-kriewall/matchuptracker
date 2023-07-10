package com.example.matchuptracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Matchup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String playerOneName;
    private Deck playerOneDeck;
    private String playerTwoName;
    private String playerTwoDeck;
    private String startingPlayer;
    @NonNull
    private String winningDeck;
    private String format;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOne) {
        this.playerOneName = playerOne;
    }

    public Deck getPlayerOneDeck() {
        return playerOneDeck;
    }

    public void setPlayerOneDeck(Deck playerOneDeck) {
        this.playerOneDeck = playerOneDeck;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwo) {
        this.playerTwoName = playerTwo;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


//    @Override
//    public String toString() {
//        return "Matchup{" +
//                "id=" + id +
//                ", playerOneName='" + playerOneName + '\'' +
//                ", playerOneDeck='" + playerOneDeck + '\'' +
//                ", playerTwoName='" + playerTwoName + '\'' +
//                ", playerTwoDeck='" + playerTwoDeck + '\'' +
//                ", startingPlayer='" + startingPlayer + '\'' +
//                ", winningDeck='" + winningDeck + '\'' +
//                ", format='" + format + '\'' +
//                ", notes='" + notes + '\'' +
//                '}';
//    }
}
