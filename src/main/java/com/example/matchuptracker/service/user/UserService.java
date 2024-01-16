package com.example.matchuptracker.service.user;

import com.example.matchuptracker.model.User;

import java.util.Optional;

public interface UserService {

    User updateUserRole(String email, String newRole);

    String getOrAddUserRole(String email, String username);

    Optional<User> findUserByEmail(String email);
}
