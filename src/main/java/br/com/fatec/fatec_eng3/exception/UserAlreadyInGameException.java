package br.com.fatec.fatec_eng3.exception;

public class UserAlreadyInGameException extends RuntimeException {

    public UserAlreadyInGameException(String message) {
        super(message);
    }
  
}
