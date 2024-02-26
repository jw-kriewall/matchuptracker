package com.example.matchuptracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private Deck playerOneDeck;
    private String playerTwoName;
    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private Deck playerTwoDeck;
    private String startingPlayer;
    @NonNull
    private String winningDeck;
    private String format;
    @Lob  // This annotation is useful especially for BLOB or CLOB data types
    @Column(name = "notes", columnDefinition="LONGTEXT")
    private String notes;
    private Date createdOn;
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private User createdBy;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

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

    public Deck getPlayerTwoDeck() {
        return playerTwoDeck;
    }

    public void setPlayerTwoDeck(Deck playerTwoDeck) {
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
