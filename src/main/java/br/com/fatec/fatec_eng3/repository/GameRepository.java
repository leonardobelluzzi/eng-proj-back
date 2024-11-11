package br.com.fatec.fatec_eng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatec.fatec_eng3.service.Game;


public interface GameRepository extends JpaRepository<Game, Long> {

    @Modifying
    @Query("UPDATE Game g SET g.pointPlayerOne = g.pointPlayerOne + :points WHERE g.id = :id")
    void incrementPointPlayerOne(@Param("id") Long id, @Param("points") Long points);

    @Modifying
    @Query("UPDATE Game g SET g.pointPlayerTwo = g.pointPlayerTwo + :points WHERE g.id = :id")
    void incrementPointPlayerTwo(@Param("id") Long id, @Param("points") Long points);

}