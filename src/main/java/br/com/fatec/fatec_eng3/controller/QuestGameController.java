package br.com.fatec.fatec_eng3.controller;

import javax.naming.NameNotFoundException;
import javax.naming.NoPermissionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.fatec_eng3.controller.dto.CreateGame;
import br.com.fatec.fatec_eng3.controller.dto.ErrorResponseQuest;
import br.com.fatec.fatec_eng3.controller.dto.JoinGame;
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
      return ResponseEntity.ok(produtoService.createNewGame(createGame));
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
  public ResponseEntity<?> joinGame(@RequestBody @Validated JoinGame joinGame) {
    try {
      return ResponseEntity.ok(produtoService.joinGame(joinGame));
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

  @GetMapping("/findGame/{key}")
  public ResponseEntity<?> findGame(@PathVariable String key) {
    try {
      return ResponseEntity.ok(produtoService.findGame(key));
    } catch (NoPermissionException e) {
      // caso o game nao tenha terminado
      return ResponseEntity.noContent().build();
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

}
