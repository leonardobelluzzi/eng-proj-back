package br.com.fatec.fatec_eng3.model;

import jakarta.persistence.*;

@Entity
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long coins = 0L;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCoins() {
    return coins;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addCoins(Long amount) {
    if (amount > 0) {
      this.coins += amount;
    }
  }

  public void removeCoins(Long amount) {
    if (amount > 0 && this.coins >= amount) {
      this.coins -= amount;
    }
  }
}