package br.com.fatec.fatec_eng3.exception;

public class GameAlreadyCompleteException extends RuntimeException {

    public GameAlreadyCompleteException(String message) {
        super(message);
    }
  
}
