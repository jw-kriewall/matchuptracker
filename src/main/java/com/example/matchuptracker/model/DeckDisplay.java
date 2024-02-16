package com.example.matchuptracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeckDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String value;
    private String label;
    private String format; // This is optional, can be null
    private List<String> sprites;

    @ManyToOne
    @JsonBackReference
//    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getSprites() {
        return sprites;
    }

    public void setSprites(List<String> sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return "DeckDisplay{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", format='" + format + '\'' +
                // Reference user by ID or email to avoid recursion
                ", userId=" + (user != null ? user.getEmail() : null) +
                '}';
    }

}
