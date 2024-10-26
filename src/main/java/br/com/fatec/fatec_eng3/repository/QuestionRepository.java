package br.com.fatec.fatec_eng3.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.fatec_eng3.model.CategorizationQuest;
import br.com.fatec.fatec_eng3.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    public List<Question> findByCatText(CategorizationQuest cat);
}
