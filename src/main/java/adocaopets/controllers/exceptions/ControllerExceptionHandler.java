package adocaopets.controllers.exceptions;


import adocaopets.exceptions.ExcecaoDeViolacaoDeIntegridadeDeDados;
import adocaopets.exceptions.ValidacaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExcecaoDeViolacaoDeIntegridadeDeDados.class)
    public ResponseEntity<StandardError> objectNotFoundException(ExcecaoDeViolacaoDeIntegridadeDeDados ex, HttpServletRequest request) {
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(ValidacaoException ex, HttpServletRequest request) {
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
