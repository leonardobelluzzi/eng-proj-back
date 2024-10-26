package br.com.fatec.fatec_eng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.fatec_eng3.service.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}