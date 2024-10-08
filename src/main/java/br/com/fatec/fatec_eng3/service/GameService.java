package br.com.fatec.fatec_eng3.service;

import br.com.fatec.fatec_eng3.controller.dto.CreateGame;
import br.com.fatec.fatec_eng3.controller.dto.JoinGame;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.naming.NameNotFoundException;
import javax.naming.NoPermissionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class GameService {

  private Map<String, Game> data;

  public GameService() {
    this.data = new HashMap<String, Game>();
  }

  public Game createNewGame(CreateGame create) {
    Game game = genrategenerateNewGame(create.getNamePlayerOne());

    data.put(game.getKey(), game);

    return game;
  }

  public Game joinGame(JoinGame join) throws NameNotFoundException {
    Game game = data.get(join.getJoinKey());

    if (game == null) {
      throw new NameNotFoundException(
        "Game not found to key " + join.getJoinKey()
      );
    }

    game.setNamePlayerTwo(join.getJoinNamePlayerTwo());

    data.put(game.getKey(), game);

    return game;
  }

  public Game genrategenerateNewGame(String playerOneName) {
    Game game = Game
      .builder()
      .key(generateNewChave())
      .gameStatus(GameStatus.CREATED)
      .namePlayerOne(playerOneName)
      .build();

    return game;
  }

  public String generateNewChave() {
    return UUID.randomUUID().toString();
  }

  public Object findGame(String key)
    throws NoPermissionException, NameNotFoundException {
    Game game = data.get(key);

    if (game == null) {
      throw new NameNotFoundException("Game not found to key " + key);
    } else if (game.getGameStatus() != GameStatus.FINISHED) {
      throw new NoPermissionException("Jogo nao terminou");
    }
    return game;
  }

  public Game finishGame(Game game) throws NameNotFoundException {
    Game gameAux = data.get(game.getKey());

    if (gameAux == null) {
      throw new NameNotFoundException("Game not found to key " + game.getKey());
    }

    //Atualizar player 1
    if (
      StringUtils.equals(game.getNamePlayerOne(), gameAux.getNamePlayerOne())
    ) {
      gameAux.setPointPlayerOne(game.getPointPlayerOne());
    }

    //Atualizar player 2
    if (
      StringUtils.equals(game.getNamePlayerTwo(), gameAux.getNamePlayerTwo())
    ) {
      gameAux.setPointPlayerOne(game.getPointPlayerTwo());
    }

    //Verificar se o jogo ja terminou
    data.put(game.getKey(), gameAux);

    return gameAux;
  }
}
