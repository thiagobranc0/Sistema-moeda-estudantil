package br.edu.pucminas.sistema_moeda_estudantil.infra.handler;

import br.edu.pucminas.sistema_moeda_estudantil.model.ErrorResponse;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.EmpresaForbiddenException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.UserNotFoundException;
import br.edu.pucminas.sistema_moeda_estudantil.model.domain.exceptions.ValorInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        ).timestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        ).timestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(EmpresaForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleEmpresaForbiddenException(EmpresaForbiddenException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()
        ).timestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleValorInvalidoException(ValorInvalidoException exception) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                exception.getMessage()
        ).timestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

}
