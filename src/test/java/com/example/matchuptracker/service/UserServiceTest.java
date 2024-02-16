package com.example.matchuptracker.service;

import com.example.matchuptracker.exception.DuplicateDeckDisplayException;
import com.example.matchuptracker.model.DeckDisplay;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.repository.DeckDisplayRepository;
import com.example.matchuptracker.repository.UserRepository;
import com.example.matchuptracker.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeckDisplayRepository deckDisplayRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserRole_UserExists_UpdatesRole() {
        String email = "test@example.com";
        String newRole = "admin";
        User user = new User();
        user.setEmail(email);
        user.setRole("user");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User updatedUser = userService.updateUserRole(email, newRole);

        verify(userRepository).save(user);
        assertEquals(newRole, updatedUser.getRole());
    }

    @Test
    void updateUserRole_UserDoesNotExist_ThrowsException() {
        String email = "nonexistent@example.com";
        assertThrows(RuntimeException.class, () -> userService.updateUserRole(email, "admin"));
    }

    @Test
    void getOrAddUserRole_UserExists_ReturnsExistingRole() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setRole("user");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        String role = userService.getOrAddUserRole(email, "username");

        assertEquals("user", role);
    }

    @Test
    void getOrAddUserRole_UserDoesNotExist_AddsUser() {
        String email = "new@example.com";
        String username = "newUser";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        userService.getOrAddUserRole(email, username);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void findUserByEmail_UserExists_ReturnsUser() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserByEmail(email);

        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void findDeckDisplaysByEmail_UserExists_ReturnsDeckDisplays() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        DeckDisplay deckDisplay = new DeckDisplay();
        deckDisplay.setValue("Deck1");
        user.setDeckDisplays(Collections.singletonList(deckDisplay));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        var deckDisplays = userService.findDeckDisplaysByEmail(email);

        assertFalse(deckDisplays.isEmpty());
        assertEquals(1, deckDisplays.size());
        assertEquals("Deck1", deckDisplays.get(0).getValue());
    }

    @Test
    void addDeckDisplayToUserByEmail_DeckDisplayExists_ThrowsException() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        DeckDisplay existingDeckDisplay = new DeckDisplay();
        existingDeckDisplay.setValue("ExistingDeck");
        user.setDeckDisplays(new ArrayList<>(Collections.singletonList(existingDeckDisplay)));

        DeckDisplay newDeckDisplay = new DeckDisplay();
        newDeckDisplay.setValue("ExistingDeck");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(DuplicateDeckDisplayException.class, () -> userService.addDeckDisplayToUserByEmail(email, newDeckDisplay));
    }

    @Test
    void addDeckDisplayToUserByEmail_DeckDisplayDoesNotExist_AddsDeckDisplay() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);

        user.setDeckDisplays(new ArrayList<>());

        DeckDisplay newDeckDisplay = new DeckDisplay();
        newDeckDisplay.setValue("NewDeck");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<DeckDisplay> addedDeckDisplay = userService.addDeckDisplayToUserByEmail(email, newDeckDisplay);

        assertTrue(addedDeckDisplay.isPresent());
        assertEquals("NewDeck", addedDeckDisplay.get().getValue());
        verify(userRepository).save(user);
    }

}


