package br.com.fatec.fatec_eng3.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

  private String key;

  private GameStatus gameStatus;

  private String namePlayerOne;

  private String namePlayerTwo;

  private Integer pointPlayerOne;

  private Integer pointPlayerTwo;
}
