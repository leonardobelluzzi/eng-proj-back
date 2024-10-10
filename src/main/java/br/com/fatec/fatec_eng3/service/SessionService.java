package br.com.fatec.fatec_eng3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.fatec.fatec_eng3.model.Session;
import br.com.fatec.fatec_eng3.model.User;
import br.com.fatec.fatec_eng3.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(generateToken());
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(2));
        return sessionRepository.save(session);
    }

    public Optional<Session> findByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    public boolean isSessionValid(Session session) {
        return session.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public void invalidateSession(Session session) {
        sessionRepository.delete(session);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}