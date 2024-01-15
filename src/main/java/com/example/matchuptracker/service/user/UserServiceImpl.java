package com.example.matchuptracker.service.user;

import com.example.matchuptracker.model.User;
import com.example.matchuptracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    public UserRepository getRepository() {
        return userRepository;
    }

    @Autowired
    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        //Do I need to set username and other attributes?
        User user;
        if (!optionalUser.isPresent()) {
            user = new User();
            user.setEmail(email);
            user.setRole("user");
            user.setUsername(username);
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
}
