package com.example.matchuptracker.service.user;

import com.example.matchuptracker.model.DeckDisplay;
import com.example.matchuptracker.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User updateUserRole(String email, String newRole);

    String getOrAddUserRole(String email, String username);

    Optional<User> findUserByEmail(String email);

    List<DeckDisplay> findDeckDisplaysByEmail(String email);

    DeckDisplay addDeckDisplayToUserByEmail(String email, DeckDisplay deckDisplay);
}
