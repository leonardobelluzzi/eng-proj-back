package br.com.fatec.fatec_eng3.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fatec.fatec_eng3.model.Answer;
import br.com.fatec.fatec_eng3.model.CategorizationQuest;
import br.com.fatec.fatec_eng3.model.Question;
import br.com.fatec.fatec_eng3.repository.AnswerRepository;
import br.com.fatec.fatec_eng3.repository.QuestionRepository;

@Service
public class StartService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private String carga = " [\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual é o maior país do mundo em área territorial?\",\n" + //
            "      \"opcoes\": [\"Canadá\", \"China\", \"Brasil\", \"Rússia\"],\n" + //
            "      \"resposta\": \"Rússia\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual país sediou a Copa do Mundo de Futebol em 2014?\",\n" + //
            "      \"opcoes\": [\"Brasil\", \"Alemanha\", \"França\", \"Itália\"],\n" + //
            "      \"resposta\": \"Brasil\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual elemento químico tem o símbolo “O”?\",\n" + //
            "      \"opcoes\": [\"Ouro\", \"Oxigênio\", \"Ósmio\", \"Oxido\"],\n" + //
            "      \"resposta\": \"Oxigênio\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual é o país famoso por ter a forma de uma bota?\",\n" + //
            "      \"opcoes\": [\"Espanha\", \"Itália\", \"Grécia\", \"França\"],\n" + //
            "      \"resposta\": \"Itália\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual artista é conhecido por sua obra “A Noite Estrelada”?\",\n" + //
            "      \"opcoes\": [\"Pablo Picasso\", \"Vincent van Gogh\", \"Claude Monet\", \"Salvador Dalí\"],\n" + //
            "      \"resposta\": \"Vincent van Gogh\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual é a cidade mais populosa da África?\",\n" + //
            "      \"opcoes\": [\"Cairo\", \"Lagos\", \"Kinshasa\", \"Johannesburg\"],\n" + //
            "      \"resposta\": \"Lagos\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual país tem mais fusos horários?\",\n" + //
            "      \"opcoes\": [\"Rússia\", \"Estados Unidos\", \"França\", \"China\"],\n" + //
            "      \"resposta\": \"França\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Em que ano aconteceu a queda do Muro de Berlim?\",\n" + //
            "      \"opcoes\": [\"1987\", \"1988\", \"1989\", \"1990\"],\n" + //
            "      \"resposta\": \"1989\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual oceano é o menor do mundo?\",\n" + //
            "      \"opcoes\": [\"Oceano Atlântico\", \"Oceano Ártico\", \"Oceano Índico\", \"Oceano Pacífico\"],\n" + //
            "      \"resposta\": \"Oceano Ártico\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual é o país com maior número de Patrimônios Mundiais da UNESCO?\",\n" + //
            "      \"opcoes\": [\"Itália\", \"China\", \"Índia\", \"México\"],\n" + //
            "      \"resposta\": \"Itália\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Em qual cidade se encontra o Burj Khalifa, o prédio mais alto do mundo?\",\n" + //
            "      \"opcoes\": [\"Nova York\", \"Xangai\", \"Dubai\", \"Tóquio\"],\n" + //
            "      \"resposta\": \"Dubai\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Quem foi o primeiro presidente dos Estados Unidos?\",\n" + //
            "      \"opcoes\": [\"John Adams\", \"Thomas Jefferson\", \"Abraham Lincoln\", \"George Washington\"],\n" + //
            "      \"resposta\": \"George Washington\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Mundo\",\n" + //
            "      \"pergunta\": \"Qual é a capital da Nova Zelândia?\",\n" + //
            "      \"opcoes\": [\"Auckland\", \"Wellington\", \"Christchurch\", \"Queenstown\"],\n" + //
            "      \"resposta\": \"Wellington\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Quem escreveu a peça 'Hamlet'?\",\n" + //
            "      \"opcoes\": [\"Oscar Wilde\", \"William Shakespeare\", \"Charles Dickens\", \"Jane Austen\"],\n" + //
            "      \"resposta\": \"William Shakespeare\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual pintor é conhecido por cortar uma parte da própria orelha?\",\n" + //
            "      \"opcoes\": [\"Leonardo da Vinci\", \"Pablo Picasso\", \"Vincent van Gogh\", \"Salvador Dalí\"],\n" + //
            "      \"resposta\": \"Vincent van Gogh\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual é o nome verdadeiro da cantora Lady Gaga?\",\n" + //
            "      \"opcoes\": [\"Stefani Joanne Angelina Germanotta\", \"Madonna Louise Ciccone\", \"Katheryn Elizabeth Hudson\", \"Alecia Beth Moore\"],\n"
            + //
            "      \"resposta\": \"Stefani Joanne Angelina Germanotta\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Quem é o autor do livro 'Dom Casmurro'?\",\n" + //
            "      \"opcoes\": [\"José de Alencar\", \"Mário de Andrade\", \"Machado de Assis\", \"Jorge Amado\"],\n" + //
            "      \"resposta\": \"Machado de Assis\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual filme de Alfred Hitchcock foi lançado em 1960 e é considerado um clássico do terror?\",\n"
            + //
            "      \"opcoes\": [\"Vertigo\", \"Rear Window\", \"North by Northwest\", \"Psycho\"],\n" + //
            "      \"resposta\": \"Psycho\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Quem é o autor da série 'Game of Thrones'?\",\n" + //
            "      \"opcoes\": [\"J.K. Rowling\", \"George R.R. Martin\", \"J.R.R. Tolkien\", \"C.S. Lewis\"],\n" + //
            "      \"resposta\": \"George R.R. Martin\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual artista lançou o álbum 'Thriller' em 1982?\",\n" + //
            "      \"opcoes\": [\"Prince\", \"Michael Jackson\", \"Madonna\", \"David Bowie\"],\n" + //
            "      \"resposta\": \"Michael Jackson\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Artes\",\n" + //
            "      \"pergunta\": \"Qual artista brasileiro é conhecido como o 'Rei do Baião'?\",\n" + //
            "      \"opcoes\": [\"Caetano Veloso\", \"Gilberto Gil\", \"Luiz Gonzaga\", \"Chico Buarque\"],\n" + //
            "      \"resposta\": \"Luiz Gonzaga\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual é o país de origem do sushi?\",\n" + //
            "      \"opcoes\": [\"China\", \"Japão\", \"Coreia do Sul\", \"Tailândia\"],\n" + //
            "      \"resposta\": \"Japão\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual animal é conhecido como o “rei da selva”?\",\n" + //
            "      \"opcoes\": [\"Leão\", \"Elefante\", \"Tigre\", \"Urso\"],\n" + //
            "      \"resposta\": \"Leão\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual cidade é conhecida como a 'capital mundial da música country'?\",\n" + //
            "      \"opcoes\": [\"Memphis\", \"Austin\", \"Nashville\", \"New Orleans\"],\n" + //
            "      \"resposta\": \"Nashville\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual é o maior planeta do Sistema Solar?\",\n" + //
            "      \"opcoes\": [\"Saturno\", \"Terra\", \"Júpiter\", \"Marte\"],\n" + //
            "      \"resposta\": \"Júpiter\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Em que ano foi fundada a Netflix?\",\n" + //
            "      \"opcoes\": [\"1995\", \"1997\", \"1999\", \"2001\"],\n" + //
            "      \"resposta\": \"1997\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual é a língua mais falada no mundo?\",\n" + //
            "      \"opcoes\": [\"Inglês\", \"Espanhol\", \"Mandarim\", \"Hindi\"],\n" + //
            "      \"resposta\": \"Mandarim\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Quem inventou o telefone?\",\n" + //
            "      \"opcoes\": [\"Nikola Tesla\", \"Alexander Graham Bell\", \"Thomas Edison\", \"Guglielmo Marconi\"],\n"
            + //
            "      \"resposta\": \"Alexander Graham Bell\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Variedades\",\n" + //
            "      \"pergunta\": \"Qual é o livro mais vendido de todos os tempos?\",\n" + //
            "      \"opcoes\": [\"O Senhor dos Anéis\", \"O Pequeno Príncipe\", \"Don Quixote\", \"A Bíblia\"],\n" + //
            "      \"resposta\": \"A Bíblia\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Em que ano as mulheres conquistaram o direito de voto no Brasil?\",\n" + //
            "      \"opcoes\": [\"1914\", \"1928\", \"1932\", \"1945\"],\n" + //
            "      \"resposta\": \"1932\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Quem foi o líder dos direitos civis que fez o discurso “Eu Tenho um Sonho”?\",\n" + //
            "      \"opcoes\": [\"Malcolm X\", \"Nelson Mandela\", \"Martin Luther King Jr.\", \"Mahatma Gandhi\"],\n" + //
            "      \"resposta\": \"Martin Luther King Jr.\"\n" + //
            "    }, {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Qual foi o primeiro país a permitir o casamento entre pessoas do mesmo sexo?\",\n" + //
            "      \"opcoes\": [\"Canadá\", \"Espanha\", \"Países Baixos\", \"Bélgica\"],\n" + //
            "      \"resposta\": \"Países Baixos\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Em que ano foi derrubado o apartheid na África do Sul?\",\n" + //
            "      \"opcoes\": [\"1989\", \"1991\", \"1994\", \"1997\"],\n" + //
            "      \"resposta\": \"1994\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Qual é o nome da teoria econômica que defende a intervenção mínima do governo na economia?\",\n"
            + //
            "      \"opcoes\": [\"Keynesianismo\", \"Marxismo\", \"Liberalismo\", \"Monetarismo\"],\n" + //
            "      \"resposta\": \"Liberalismo\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Quem foi a primeira mulher eleita chefe de Estado?\",\n" + //
            "      \"opcoes\": [\"Sirimavo Bandaranaike\", \"Indira Gandhi\", \"Margaret Thatcher\", \"Golda Meir\"],\n"
            + //
            "      \"resposta\": \"Sirimavo Bandaranaike\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Qual é a religião predominante na Indonésia?\",\n" + //
            "      \"opcoes\": [\"Hinduísmo\", \"Budismo\", \"Islamismo\", \"Cristianismo\"],\n" + //
            "      \"resposta\": \"Islamismo\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Sociedade\",\n" + //
            "      \"pergunta\": \"Qual é o nome do tratado que acabou com a Primeira Guerra Mundial?\",\n" + //
            "      \"opcoes\": [\"Tratado de Versalhes\", \"Tratado de Tordesilhas\", \"Tratado de Paris\", \"Tratado de Madri\"],\n"
            + //
            "      \"resposta\": \"Tratado de Versalhes\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Em que ano Pelé ganhou sua primeira Copa do Mundo?\",\n" + //
            "      \"opcoes\": [\"1954\", \"1958\", \"1962\", \"1970\"],\n" + //
            "      \"resposta\": \"1958\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Quem é o atleta com mais medalhas olímpicas de todos os tempos?\",\n" + //
            "      \"opcoes\": [\"Michael Phelps\", \"Usain Bolt\", \"Carl Lewis\", \"Simone Biles\"],\n" + //
            "      \"resposta\": \"Michael Phelps\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual é a duração de uma maratona tradicional?\",\n" + //
            "      \"opcoes\": [\"40 km\", \"41.195 km\", \"39 km\", \"42.195 km\"],\n" + //
            "      \"resposta\": \"42.195 km\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual jogador é o recordista em títulos de Super Bowl na história da NFL?\",\n" + //
            "      \"opcoes\": [\"Peyton Manning\", \"Joe Montana\", \"Tom Brady\", \"Patrick Mahomes\"],\n" + //
            "      \"resposta\": \"Tom Brady\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual é o único país a ter participado de todas as Copas do Mundo?\",\n" + //
            "      \"opcoes\": [\"Itália\", \"Argentina\", \"Alemanha\", \"Brasil\"],\n" + //
            "      \"resposta\": \"Brasil\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Quem é o jogador de basquete com mais títulos da NBA?\",\n" + //
            "      \"opcoes\": [\"Michael Jordan\", \"LeBron James\", \"Bill Russell\", \"Kobe Bryant\"],\n" + //
            "      \"resposta\": \"Bill Russell\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual equipe de Fórmula 1 tem a maior quantidade de vitórias na história da categoria?\",\n"
            + //
            "      \"opcoes\": [\"Ferrari\", \"McLaren\", \"Mercedes\", \"Red Bull Racing\"],\n" + //
            "      \"resposta\": \"Ferrari\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Esporte\",\n" + //
            "      \"pergunta\": \"Qual dos seguintes esportes é conhecido por ter a maior duração de uma partida profissional regular?\",\n"
            + //
            "      \"opcoes\": [\"Futebol Americano\", \"Críquete\", \"Hóquei no Gelo\", \"Rugby\"],\n" + //
            "      \"resposta\": \"Críquete\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Quem desenvolveu a teoria da evolução das espécies?\",\n" + //
            "      \"opcoes\": [\"Gregor Mendel\", \"Charles Darwin\", \"Alfred Wallace\", \"Louis Pasteur\"],\n" + //
            "      \"resposta\": \"Charles Darwin\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual é o termo que se refere a um sistema de inteligência artificial projetado para simular o comportamento humano em conversas naturais?\",\n"
            + //
            "      \"opcoes\": [\"Machine Learning\", \"Processamento de Linguagem Natural (NLP)\", \"Rede Neural\", \"Robótica\"],\n"
            + //
            "      \"resposta\": \"Processamento de Linguagem Natural (NLP)\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual é a unidade de medida usada para a força elétrica?\",\n" + //
            "      \"opcoes\": [\"Ampere\", \"Volt\", \"Watt\", \"Ohm\"],\n" + //
            "      \"resposta\": \"Volt\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual cientista propôs as três leis do movimento?\",\n" + //
            "      \"opcoes\": [\"Galileo Galilei\", \"Isaac Newton\", \"Albert Einstein\", \"James Clerk Maxwell\"],\n"
            + //
            "      \"resposta\": \"Isaac Newton\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual é o nome do primeiro satélite artificial lançado pela União Soviética em 1957?\",\n"
            + //
            "      \"opcoes\": [\"Apollo 11\", \"Sputnik 1\", \"Voyager 1\", \"Hubble\"],\n" + //
            "      \"resposta\": \"Sputnik 1\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual é o nome da partícula subatômica que possui carga negativa?\",\n" + //
            "      \"opcoes\": [\"Próton\", \"Neutrino\", \"Nêutron\", \"Elétron\"],\n" + //
            "      \"resposta\": \"Elétron\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual tecnologia é usada para criar redes descentralizadas e permite a transferência segura de dados sem a necessidade de intermediários?\",\n"
            + //
            "      \"opcoes\": [\"Cloud Computing\", \"Blockchain\", \"Internet das Coisas (IoT)\", \"Virtualização\"],\n"
            + //
            "      \"resposta\": \"Blockchain\"\n" + //
            "    },\n" + //
            "    {\n" + //
            "      \"categoria\": \"Ciencia\",\n" + //
            "      \"pergunta\": \"Qual é o nome da camada mais externa da Terra?\",\n" + //
            "      \"opcoes\": [\"Manto\", \"Núcleo\", \"Litosfera\", \"Crosta\"],\n" + //
            "      \"resposta\": \"Crosta\"\n" + //
            "    }\n" + //
            "  ]";

    public void initilizationQuest() throws JsonMappingException, JsonProcessingException {

        questionRepository.deleteAll();
        answerRepository.deleteAll();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Pergunta> perguntas = objectMapper.readValue(carga, new TypeReference<List<Pergunta>>() {
        });

        Long idPergunta = 1l;
        Long idQuestion = 1l;

        for (Pergunta pergunta : perguntas) {

            Question question = Question.builder()
                    .id(idQuestion) // Você pode ajustar o ID conforme necessário
                    .text(pergunta.getPergunta())
                    .catText(CategorizationQuest.valueOf(pergunta.getCategoria()))
                    .answers(null)
                    .build();
            
            questionRepository.save(question);
            idQuestion++;

            Answer a1 = Answer.builder().id(idPergunta).text(pergunta.getOpcoes().get(0))
                    .correct(StringUtils.equals(pergunta.getOpcoes().get(0), pergunta.getResposta())).question(question)
                    .build();
            idPergunta++;
            Answer a2 = Answer.builder().id(idPergunta).text(pergunta.getOpcoes().get(1))
                    .correct(StringUtils.equals(pergunta.getOpcoes().get(1), pergunta.getResposta())).question(question)
                    .build();
            idPergunta++;
            Answer a3 = Answer.builder().id(idPergunta).text(pergunta.getOpcoes().get(2))
                    .correct(StringUtils.equals(pergunta.getOpcoes().get(2), pergunta.getResposta())).question(question)
                    .build();
            idPergunta++;
            Answer a4 = Answer.builder().id(idPergunta).text(pergunta.getOpcoes().get(3))
                    .correct(StringUtils.equals(pergunta.getOpcoes().get(3), pergunta.getResposta())).question(question)
                    .build();
            idPergunta++;

            List<Answer> answers = List.of(a1, a2, a3, a4);

            // Pergunta

            question.setAnswers(answers);

            answerRepository.save(a1);
            answerRepository.save(a2);
            answerRepository.save(a3);
            answerRepository.save(a4);
            questionRepository.save(question);

        }

    }

}