package br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }
}
