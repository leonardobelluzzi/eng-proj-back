//package br.com.fatec.fatec_eng3.controller;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

//import br.com.fatec.fatec_eng3.exception.UserAlreadyInGameException;
//import br.com.fatec.fatec_eng3.model.Game;
//import br.com.fatec.fatec_eng3.service.GameService;
//import jakarta.servlet.http.HttpServletRequest;

//@RestController
//@RequestMapping("/api/game")
//public class GameController {

//  @Autowired
//  private GameService gameService;

//  @PostMapping("/")
//  public void createGame(HttpServletRequest request) {
//    String userId = (String) request.getAttribute("userId");

//    try {
//      Game game = gameService.createGame(Long.parseLong(userId));
//    } catch (Exception e) {
//      if (e instanceof UserAlreadyInGameException) {
//        return ResponseEntity.unprocessableEntity().body("User already in an active game.");
//      }
//    }
//  }
  
//}
