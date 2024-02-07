package com.example.matchuptracker.service.user;

import com.example.matchuptracker.model.DeckDisplay;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.repository.DeckDisplayRepository;
import com.example.matchuptracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private DeckDisplayRepository deckDisplayRepository;
    public UserRepository getRepository() {
        return userRepository;
    }

    @Autowired
    public void setRepository(UserRepository userRepository, DeckDisplayRepository deckDisplayRepository) {
        this.userRepository = userRepository;
        this.deckDisplayRepository = deckDisplayRepository;
    }


    @Override
    public User updateUserRole(String email, String newRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole);
        userRepository.save(user);
        return user;
    }

    @Override
    public String getOrAddUserRole(String email, String username) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;
        if (!optionalUser.isPresent()) {
            user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setRole("user");

            userRepository.save(user);
        } else {
            user = optionalUser.get();
        }
        return user.getRole();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<DeckDisplay> findDeckDisplaysByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getDeckDisplays)
                .orElse(Collections.emptyList());
    }

    @Transactional
    public DeckDisplay addDeckDisplayToUserByEmail(String email, DeckDisplay newDeckDisplay) {
        return userRepository.findByEmail(email).map(user -> {
            newDeckDisplay.setUser(user);
            return deckDisplayRepository.save(newDeckDisplay);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
