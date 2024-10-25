package br.com.fatec.fatec_eng3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fatec.fatec_eng3.model.Game;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
  Optional<Game> findByGameId(String gameId);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END " +
           "FROM Game g WHERE (g.firstPlayer.id = :userId OR g.secondPlayer.id = :userId) AND g.status <> 'FINISHED'")
    boolean isUserAlreadyInActiveGame(@Param("userId") Long userId);
}