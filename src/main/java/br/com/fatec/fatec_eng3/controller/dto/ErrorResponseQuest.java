package br.com.fatec.fatec_eng3.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseQuest {

  private String error;

  private String stack;
}
