package br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
