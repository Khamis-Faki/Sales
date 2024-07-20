package com.project.pp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.pp.model.Login;
import com.project.pp.repository.LoginRepository;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/logins")
    public ResponseEntity<List<Login>> getAllLogins() {
        List<Login> logins = loginRepository.findAll();
        return new ResponseEntity<>(logins, HttpStatus.OK);
    }

    @GetMapping("/logins/{id}")
    public ResponseEntity<Login> getLoginById(@PathVariable Long id) {
        Optional<Login> login = loginRepository.findById(id);
        return login.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Login login) {
        if (loginRepository.findByUsername(login.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        loginRepository.save(login);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
        Optional<Login> user = loginRepository.findByUsername(login.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(login.getPassword())) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
