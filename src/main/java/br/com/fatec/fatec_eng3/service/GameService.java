package br.com.fatec.fatec_eng3.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.model.Points;
import br.com.fatec.fatec_eng3.model.Wallet;
import br.com.fatec.fatec_eng3.repository.GameRepository;
import jakarta.transaction.Transactional;

@Service
@Scope("prototype")
public class GameService {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private WalletService walletService;

  public Game joinGame(Long idJogo, Long idPlayerTwo, String playerTwoUserName) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(idJogo);

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + idJogo);
    }

    Game gameSource = game.get();

    if (gameSource.getGameStatus() != GameStatus.CREATED) {

      throw new NameNotFoundException(
          "Game not playing or finished " + idJogo);

    }

    gameSource.setGameStatus(GameStatus.PLAYING);
    gameSource.setIdPlayerTwo(idPlayerTwo);
    gameSource.setPlayerTwoUserName(playerTwoUserName);

    walletService.spendCoins(idPlayerTwo, 50L);

    return gameRepository.save(gameSource);
  }

  public Game generateNewGame(Long playerOneId, String playerOneUserName) {

    Game game = Game
        .builder()
        .gameStatus(GameStatus.CREATED)
        .idPlayerOne(playerOneId)
        .playerOneUserName(playerOneUserName)
        .questions(questionService.getAnswersToGame())
        .pointPlayerOne(0)
        .pointPlayerTwo(0)
        .build();

    game = gameRepository.save(game);

    game.setIdFormat(String.format("%06d", game.getId()));

    walletService.spendCoins(playerOneId, 50L);

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

  public Game searchGame(String id) throws NameNotFoundException {
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

  public synchronized Game finishGame(Game gameSource, Long idPlayer) throws NameNotFoundException {
    Optional<Game> game = gameRepository.findById(gameSource.getId());

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + gameSource.getId());
    }

    Game gameSourceDB = game.get();

    if (gameSourceDB.getIdPlayerOne() != idPlayer && gameSourceDB.getIdPlayerTwo() != idPlayer) {
      throw new NameNotFoundException(
          "Player not in game " + gameSource.getId());
    }

    if (gameSourceDB.getIdPlayerOne() == idPlayer) {
      gameSourceDB.setPlayerOneFinishTime((new Date()).getTime());
    } else {
      gameSourceDB.setPlayerTwoFinishTime((new Date()).getTime());
    }

    if (gameSourceDB.getGameStatus() == GameStatus.PLAYING) {
      gameSourceDB.setGameStatus(GameStatus.FINISH_PLAYER);
    } else {
      gameSourceDB.setGameStatus(GameStatus.FINISHED);
      Long idWinner = gammerWinner(gameSourceDB);
      Wallet wallet = walletService.findByUserId(idWinner);
      if (wallet != null) {
        if (gameSourceDB.getIdPlayerOne() == idWinner) {
          wallet.addCoins(Long.valueOf(gameSourceDB.getPointPlayerOne()));
        } else {
          wallet.addCoins(Long.valueOf(gameSourceDB.getPointPlayerTwo()));
        }
      }
      gameSourceDB.setUserWinner(idWinner);
    }
  
    return gameRepository.save(gameSourceDB);
  }

  private Long gammerWinner(Game gameSourceDB) {
    if (gameSourceDB.getPointPlayerOne() > gameSourceDB.getPointPlayerTwo()) {
      return gameSourceDB.getIdPlayerOne();
    } else if (gameSourceDB.getPointPlayerOne() < gameSourceDB.getPointPlayerTwo()) {

      return gameSourceDB.getIdPlayerTwo();
    } else {

      if (gameSourceDB.getPlayerOneFinishTime() < gameSourceDB.getPlayerTwoFinishTime()) {

        return gameSourceDB.getIdPlayerOne();
      } else {

        return gameSourceDB.getIdPlayerTwo();
      }
    }
  }

  public synchronized Points getPoints(String id) throws NameNotFoundException {

    Optional<Game> game = gameRepository.findById(Long.valueOf(id));

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + id);
    }

    Game gameSourceDB = game.get();

    Points points = Points
        .builder()
        .idRoom(gameSourceDB.getId())
        .pointPlayerOne(gameSourceDB.getPointPlayerOne())
        .pointPlayerTwo(gameSourceDB.getPointPlayerTwo())
        .build();

    return points;
  }

  @Transactional
  public synchronized Points addPoints(String id, Long idPlayer, Long points) throws NameNotFoundException {

    Optional<Game> game = gameRepository.findById(Long.valueOf(id));

    if (!game.isPresent()) {
      throw new NameNotFoundException(
          "Game not found to key " + id);
    }

    Game gameSourceDB = game.get();

    if (gameSourceDB.getIdPlayerOne() == idPlayer) {
      gameRepository.incrementPointPlayerOne(Long.valueOf(id), points);
    } else {
      gameRepository.incrementPointPlayerTwo(Long.valueOf(id), points);
    }

    return getPoints(id);

  }
}
