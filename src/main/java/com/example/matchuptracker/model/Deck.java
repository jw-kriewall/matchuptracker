package com.example.matchuptracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cards")
    private String cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }
}
