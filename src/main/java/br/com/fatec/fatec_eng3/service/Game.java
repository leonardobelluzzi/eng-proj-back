package br.com.fatec.fatec_eng3.service;

import java.util.List;

import br.com.fatec.fatec_eng3.model.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "game")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "game_status", nullable = true)
  private GameStatus gameStatus;

  @Column(name = "id_player_one", nullable = true)
  private Long idPlayerOne;

  @Column(name = "id_player_two")
  private Long idPlayerTwo;

  @Column(name = "point_player_one", nullable = true)
  private Integer pointPlayerOne;

  @Column(name = "point_player_two", nullable = true)
  private Integer pointPlayerTwo;

  @ManyToMany
  @JoinTable(name = "game_question", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
  private List<Question> questions;

  private String idFormat;
}
