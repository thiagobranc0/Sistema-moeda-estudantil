package br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions;

public class EmpresaForbiddenException extends RuntimeException {
    public EmpresaForbiddenException(String message) {
        super(message);
    }
}
