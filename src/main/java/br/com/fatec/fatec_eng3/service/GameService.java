package br.com.fatec.fatec_eng3.service;

import br.com.fatec.fatec_eng3.exception.GameAlreadyCompleteException;
import br.com.fatec.fatec_eng3.exception.GameNotFoundException;
import br.com.fatec.fatec_eng3.exception.UserAlreadyInGameException;
import br.com.fatec.fatec_eng3.exception.UserNotFoundException;
import br.com.fatec.fatec_eng3.model.Game;
import br.com.fatec.fatec_eng3.model.GameStatus;
import br.com.fatec.fatec_eng3.model.User;
import br.com.fatec.fatec_eng3.repository.GameRepository;
import br.com.fatec.fatec_eng3.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    public Game createGame(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        if (gameRepository.isUserAlreadyInActiveGame(userId)) {
            throw new UserAlreadyInGameException("User already in an active game.");
        }

        Game game = new Game();

        game.setFirstPlayer(user.get()); 
        game.setStatus(GameStatus.WAITING_FOR_PLAYERS);
        gameRepository.save(game);

        return game;
    }

    public Game addPlayerToGame(String gameId, Long userId) throws Exception {
        Optional<Game> gameOptional = gameRepository.findByGameId(gameId);
        
        if (gameOptional.isEmpty()) {
            throw new GameNotFoundException("Game not found.");
        }

        Game game = gameOptional.get();
        if (game.getSecondPlayerReady()) {
            throw new GameAlreadyCompleteException("Game is already full.");
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        if (gameRepository.isUserAlreadyInActiveGame(userId)) {
            throw new UserAlreadyInGameException("User already in an active game.");
        }

        game.setSecondPlayer(user.get());
        game.setSecondPlayerReady(true);
        game.setStatus(GameStatus.WAITING_FOR_CONSENT);

        gameRepository.save(game);

        return game;

    }
}
