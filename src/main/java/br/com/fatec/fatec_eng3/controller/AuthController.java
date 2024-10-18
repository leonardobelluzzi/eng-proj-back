package br.com.fatec.fatec_eng3.controller;

import br.com.fatec.fatec_eng3.controller.dto.UserLoginDTO;
import br.com.fatec.fatec_eng3.controller.dto.UserRegistrationDTO;
import br.com.fatec.fatec_eng3.model.User;
import br.com.fatec.fatec_eng3.model.Session;
import br.com.fatec.fatec_eng3.service.AuthService;
import br.com.fatec.fatec_eng3.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
      try {
        User user = new User();

        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(userRegistrationDTO.getPassword());
        authService.register(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário registrado com sucesso.");
        return ResponseEntity.ok(response);
      } catch (Exception e) {
        if (e instanceof IllegalArgumentException) {
          Map<String, String> response = new HashMap<>();
          response.put("message", e.getMessage());
          return ResponseEntity.unprocessableEntity().body(response);
        }

        throw e;
      }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        Optional<User> user = authService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if (user.isPresent()) {
            Session session = sessionService.createSession(user.get());
            Map<String, String> response = new HashMap<>();
            response.put("token", session.getToken());
            response.put("id", user.get().getId().toString());
            response.put("username", user.get().getUsername());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Credenciais inválidas");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestParam String token) {
        Optional<Session> session = sessionService.findByToken(token);
        if (session.isPresent()) {
            sessionService.invalidateSession(session.get());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logout bem-sucedido.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Sessão inválida.");
            return ResponseEntity.status(422).body(response);
        }
    }
}