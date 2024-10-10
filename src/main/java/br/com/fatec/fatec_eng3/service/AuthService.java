package br.com.fatec.fatec_eng3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.model.User;
import br.com.fatec.fatec_eng3.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) {
      Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
      if (existingUser.isPresent()) {
          throw new IllegalArgumentException("O nome de usuário já está em uso.");
      }
      
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}