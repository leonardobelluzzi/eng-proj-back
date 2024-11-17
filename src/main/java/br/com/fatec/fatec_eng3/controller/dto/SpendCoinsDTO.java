package br.com.fatec.fatec_eng3.controller.dto;

import jakarta.validation.constraints.NotNull;

public class SpendCoinsDTO {
  @NotNull(message = "O número de moedas não pode ser nulo")
  Long coins;

  @NotNull(message = "O id do usuário não pode ser nulo")
  Long idUser;

  public Long getCoins() {
    return coins;
  }

  public void setCoins(Long coins) {
    this.coins = coins;
  }

  public Long getIdUser() {
    return idUser;
  }

  public void setIdUser(Long idUser) {
    this.idUser = idUser;
  }
}
