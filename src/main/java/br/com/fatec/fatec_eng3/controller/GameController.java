package br.com.fatec.fatec_eng3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.fatec_eng3.exception.GameAlreadyCompleteException;
import br.com.fatec.fatec_eng3.exception.GameNotFoundException;
import br.com.fatec.fatec_eng3.exception.UserAlreadyInGameException;
import br.com.fatec.fatec_eng3.exception.UserNotFoundException;
import br.com.fatec.fatec_eng3.model.Game;
import br.com.fatec.fatec_eng3.service.GameService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/game")
public class GameController {

  @Autowired
  private GameService gameService;

  @Autowired
    private SimpMessagingTemplate messagingTemplate;

  @PostMapping("/")
  public ResponseEntity<?> createGame(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");

    try {
      Game game = gameService.createGame(Long.parseLong(userId));

      return ResponseEntity.ok(game);
    } catch (Exception e) {
      if (e instanceof UserAlreadyInGameException) {
        return ResponseEntity.unprocessableEntity().body("User already in an active game.");
      }

      if (e instanceof UserNotFoundException) {
        return ResponseEntity.unprocessableEntity().body("User not found.");
      }

      throw e;
    }
  }

  @PostMapping("/join")
  public ResponseEntity<?> joinGame(HttpServletRequest request) throws Exception {
    String userId = (String) request.getAttribute("userId");
    String gameId = request.getParameter("gameId");

    try {
      Game game = gameService.addPlayerToGame(gameId, Long.parseLong(userId));
      messagingTemplate.convertAndSend("/topic/game/" + gameId, game);

      return ResponseEntity.ok(game);
    } catch (Exception e) {
      if (e instanceof UserNotFoundException) {
        return ResponseEntity.unprocessableEntity().body("User not found.");
      }

      if (e instanceof UserAlreadyInGameException) {
        return ResponseEntity.unprocessableEntity().body("User already in an active game.");
      }

      if (e instanceof GameAlreadyCompleteException) {
        return ResponseEntity.unprocessableEntity().body("Game is already full.");
      }

      if (e instanceof GameNotFoundException) {
        return ResponseEntity.unprocessableEntity().body("Game not found.");
      }

      throw e;
    }
  }
  
}
