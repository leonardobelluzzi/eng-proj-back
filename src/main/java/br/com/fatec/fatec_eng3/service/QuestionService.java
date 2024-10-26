package br.com.fatec.fatec_eng3.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.fatec_eng3.model.CategorizationQuest;
import br.com.fatec.fatec_eng3.model.Question;
import br.com.fatec.fatec_eng3.repository.QuestionRepository;

@Service
public class QuestionService {

  @Autowired
  private QuestionRepository questionRepository;

  // Busca de uma resposta por ID
  public Optional<Question> getAnswerById(Long id) {
    return questionRepository.findById(id);
  }

  // Busca de todas as respostas
  public List<Question> getAllAnswers() {
    return questionRepository.findAll();
  }

  // Busca de todas as respostas
  @SuppressWarnings("deprecation")
  public List<Question> getAnswersToGame() {

    // Artes
    List<Question> artes = questionRepository.findByCatText(CategorizationQuest.Artes);
    Question arte = artes.get(RandomUtils.nextInt(0, artes.size() - 1));

    // Ciencia,
    List<Question> ciencias = questionRepository.findByCatText(CategorizationQuest.Ciencia);
    Question ciencia = ciencias.get(RandomUtils.nextInt(0, ciencias.size() - 1));

    // Mundo,
    List<Question> mundos = questionRepository.findByCatText(CategorizationQuest.Mundo);
    Question mundo = mundos.get(RandomUtils.nextInt(0, mundos.size() - 1));

    // Esporte ,
    List<Question> esportes = questionRepository.findByCatText(CategorizationQuest.Esporte);
    Question esporte = esportes.get(RandomUtils.nextInt(0, esportes.size() - 1));

    // Sociedade ,
    List<Question> sociedades = questionRepository.findByCatText(CategorizationQuest.Sociedade);
    Question sociedade = sociedades.get(RandomUtils.nextInt(0, sociedades.size() - 1));

    // Variedades ,
    List<Question> variedades = questionRepository.findByCatText(CategorizationQuest.Variedades);
    Question variedade = variedades.get(RandomUtils.nextInt(0, variedades.size() - 1));

    return List.of(arte, ciencia, mundo, esporte, sociedade, variedade);

  }

}
