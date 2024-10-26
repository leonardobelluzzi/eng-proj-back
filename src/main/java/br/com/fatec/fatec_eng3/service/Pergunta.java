package br.com.fatec.fatec_eng3.service;

import java.util.List;

import lombok.Data;

@Data
public class Pergunta {

    private String categoria;
    private String pergunta;
    private List<String> opcoes;
    private String resposta;

}
