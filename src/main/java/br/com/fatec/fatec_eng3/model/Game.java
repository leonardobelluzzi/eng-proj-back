package br.com.fatec.fatec_eng3.model;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean firstPlayerReady = false;
    private Boolean secondPlayerReady = false;

    private String gameId;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @OneToOne
    private User firstPlayer;

    @OneToOne
    private User secondPlayer;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Boolean getFirstPlayerReady() {
      return firstPlayerReady;
    }

    public void setFirstPlayerReady(Boolean firstPlayerReady) {
      this.firstPlayerReady = firstPlayerReady;
    }

    public Boolean getSecondPlayerReady() {
      return secondPlayerReady;
    }

    public void setSecondPlayerReady(Boolean secondPlayerReady) {
      this.secondPlayerReady = secondPlayerReady;
    }

    public GameStatus getStatus() {
      return status;
    }

    public void setStatus(GameStatus status) {
      this.status = status;
    }

    public User getFirstPlayer() {
      return firstPlayer;
    }

    public void setFirstPlayer(User firstPlayer) {
      this.firstPlayer = firstPlayer;
    }

    public User getSecondPlayer() {
      return secondPlayer;
    }

    public void setSecondPlayer(User secondPlayer) {
      this.secondPlayer = secondPlayer;
    }

     public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
