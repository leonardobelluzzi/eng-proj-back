package br.com.fatec.fatec_eng3.service;

import java.util.Optional;
import java.util.UUID;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.repository.GameRepository;

@Service
@Scope("prototype")
public class GameService {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private GameRepository gameRepository;


  public Game joinGame(Long idJogo, Long idPlayerTwo) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(idJogo);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + idJogo);
    }

    Game gameSource = game.get();

    if (gameSource.getGameStatus() != GameStatus.CREATED){

      throw new NameNotFoundException(
          "Game not palying or finished " + idJogo);

    }

    gameSource.setGameStatus(GameStatus.PLAYING);
    gameSource.setIdPlayerTwo(idPlayerTwo);

    return gameRepository.save(gameSource);
  }

  public Game generateNewGame(Long playerOneName) {
    Game game = Game
        .builder()
        .gameStatus(GameStatus.CREATED)
        .idPlayerOne(playerOneName)
        .questions(questionService.getAnswersToGame())
        .build();

    game = gameRepository.save(game);

    game.setIdFormat(String.format("%06d", game.getId()));

    return game;
  }

  public Game canStartNewGame(Long idGame) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(idGame);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + idGame);
    }

    Game gameSource = game.get();

    if (gameSource.getGameStatus() == GameStatus.PLAYING){

      return gameSource;
    }

    return null;
    
  }

  public String generateNewChave() {
    return UUID.randomUUID().toString();
  }



}
