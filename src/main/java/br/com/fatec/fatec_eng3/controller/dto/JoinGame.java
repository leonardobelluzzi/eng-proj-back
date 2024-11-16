package br.com.fatec.fatec_eng3.controller.dto;

import lombok.Data;

@Data
public class JoinGame {

  private Long idUser;

  private String roomCode;

  private String userName;
}
