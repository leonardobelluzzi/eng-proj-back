package br.com.fatec.fatec_eng3.controller.dto;

import java.util.List;
import lombok.Data;

@Data
public class Quest {

  private String type;

  private String difficulty;

  private String category;

  private String question;

  private String awser;

  private String correct_answer;

  private List<String> incorrect_answers;
}
