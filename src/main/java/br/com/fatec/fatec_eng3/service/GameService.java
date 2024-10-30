package br.com.fatec.fatec_eng3.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.model.User;
import br.com.fatec.fatec_eng3.repository.GameRepository;

@Service
@Scope("prototype")
public class GameService {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private AuthService authService;

  public Game joinGame(Long idJogo, Long idPlayerTwo) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(idJogo);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + idJogo);
    }

    Game gameSource = game.get();

    if (gameSource.getGameStatus() != GameStatus.CREATED) {

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
        .pointPlayerOne(0)
        .pointPlayerTwo(0)
        .build();

    game = gameRepository.save(game);

    game.setIdFormat(String.format("%06d", game.getId()));

    return game;
  }

  public Boolean canStartNewGame(Long idGame) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(idGame);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + idGame);
    }

    Game gameSource = game.get();

    if (gameSource.getGameStatus() == GameStatus.PLAYING) {

      return true;
    }

    return false;

  }

  public String generateNewChave() {
    return UUID.randomUUID().toString();
  }

  public Object searchGame(String id) throws NameNotFoundException {
    Long a = Long.valueOf(id);

    Optional<Game> game = gameRepository.findById(a);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + id);
    }

    return game.get();
  }

public Game saveGame(Game gameSource) {

    return gameRepository.save(gameSource);
}

public Game finishGame(Game gameSource, Long idPlayer) {
  gameSource = gameRepository.findById(gameSource.getId()).get();

  if (gameSource.getIdPlayerOne() == idPlayer){

    gameSource.setPlayerOneFinishTime((new Date()).getTime());

  }else{

    gameSource.setPlayerTwoFinishTime((new Date()).getTime());
  }

  gameSource.setGameStatus(GameStatus.FINISH_PLAYER);

  if (gameSource.getPlayerOneFinishTime() != null && gameSource.getPlayerTwoFinishTime() != null){

    gameSource.setGameStatus(GameStatus.FINISHED);
    gameSource.setUserWinner(winner(gameSource));
  }
    
  return gameRepository.save(gameSource);
}

private Long winner(Game gameSource) {
  // TODO Auto-generated method stub
  if (gameSource.getPointPlayerOne() == gameSource.getPointPlayerTwo()){
    if (gameSource.getPlayerOneFinishTime() < gameSource.getPlayerTwoFinishTime()){

      return gameSource.getIdPlayerOne();
    }else{

      return gameSource.getIdPlayerTwo();
    }
  }else if (gameSource.getPointPlayerOne() > gameSource.getPointPlayerTwo()){
    return gameSource.getIdPlayerOne();
  }else{
    return gameSource.getIdPlayerTwo();
  }
}

}
