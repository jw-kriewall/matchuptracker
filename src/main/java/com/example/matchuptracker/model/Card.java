package com.example.matchuptracker.model;

abstract class Card {
    // A card has the following attributes:
    // Set, set#, Name,
    // language?, condition?
    String name;
    String set;
    String setNumber;
    String type;

    public Card(String name, String set, String setNumber) {
        this.name = name;
        this.set = set;
        this.setNumber = setNumber;
    }

    public void addType(String trainer) {
        this.type = type;
    }

    public String toString() {
        return name + " " + set + " " + setNumber;
    }
}
