package br.com.fatec.fatec_eng3.controller;

import javax.naming.NameNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.fatec_eng3.controller.dto.CreateGame;
import br.com.fatec.fatec_eng3.controller.dto.ErrorResponseQuest;
import br.com.fatec.fatec_eng3.controller.dto.JoinGame;
import br.com.fatec.fatec_eng3.service.Game;
import br.com.fatec.fatec_eng3.service.GameService;
import br.com.fatec.fatec_eng3.service.QuestionService;

@RestController
@RequestMapping("/quest/v1")
public class QuestGameController {

  @Autowired
  private GameService produtoService;

  @Autowired
  private QuestionService questionService;

  // Listar todos os produtos
  @PostMapping("/createGame")
  public ResponseEntity<?> createGame(@RequestBody CreateGame createGame) {
    try {
      return ResponseEntity.ok(produtoService.generateNewGame(createGame.getIdUser()));
    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/joinGame")
  public ResponseEntity<?> joinGame(@RequestBody JoinGame joinGame) {
    try {
      return ResponseEntity.ok(produtoService.joinGame(Long.valueOf(joinGame.getRoomCode()), joinGame.getIdUser()));
    } catch (NameNotFoundException e) {
      // caso naome nao exista
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/searchGame/{id}")
  public ResponseEntity<?> searchGame(@PathVariable String id) {
    try {
      return ResponseEntity.ok(produtoService.searchGame(id));
    } catch (NameNotFoundException e) {
      // caso naome nao exista
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/canStartGame/{id}")
  public ResponseEntity<?> joinGame(@PathVariable String id) {
    try {
      Boolean game = produtoService.canStartNewGame(Long.valueOf(id));

      if (game) {

        return ResponseEntity.ok("OK");
      }

      return new ResponseEntity<>("wating",
          HttpStatus.INTERNAL_SERVER_ERROR);

    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/question/{id}")
  public ResponseEntity<?> findUQestion(@PathVariable Long id) {

    try {
      return ResponseEntity.ok(questionService.getAnswerById(id));

    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @PostMapping(path = "/saveGame", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> saveGame(@RequestBody Game gameSource) {
    try {
      
      return ResponseEntity.ok(produtoService.saveGame(gameSource));
    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path = "/finishGame", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> finishGame(@RequestBody Game gameSource) {
    try {
      
      return ResponseEntity.ok(produtoService.finishGame(gameSource));
    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/questions")
  public ResponseEntity<?> findUQestions() {

    try {
      return ResponseEntity.ok(questionService.getAllAnswers());

    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/questionsToGame")
  public ResponseEntity<?> findQuestionsGame() {

    try {
      return ResponseEntity.ok(questionService.getAnswersToGame());

    } catch (Exception e) {
      return new ResponseEntity<>(
          ErrorResponseQuest
              .builder()
              .error(e.getMessage())
              .stack(ExceptionUtils.getStackTrace(e))
              .build(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
