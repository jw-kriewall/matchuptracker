package com.example.matchuptracker.controller;

import com.example.matchuptracker.model.LoginDTO;
import com.example.matchuptracker.model.User;
import com.example.matchuptracker.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping(UserController.API + UserController.VERSION + UserController.USER_ENDPOINT)
public class UserController {

    public static final String API = "/api";
    public static final String VERSION = "/v1";
    public static final String USER_ENDPOINT = "/user";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/role")
    public ResponseEntity<String> getRole(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String username = loginDTO.getUsername();
        return new ResponseEntity<>(userService.getOrAddUserRole(email, username), HttpStatus.OK);
    }

    @PostMapping("/updaterole")
    public ResponseEntity<User> updateUserRole(@RequestParam String email, String role) {
        return new ResponseEntity<>(userService.updateUserRole(email, role), HttpStatus.OK);
    }
}
