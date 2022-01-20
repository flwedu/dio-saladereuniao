package com.digital.one.saladereuniao.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Essa classe recebe Exceptions dos mais diversos tipos e retorna um
 * ResponseEntity adequado.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Método que lida quando uma exception do tipo ResourceNotFound é disparada.
     * 
     * @param exception Excessão que acabou de ser lançada
     * @param request   Dados da Requisição
     * @return Um ResponseEntity com erro código 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException exception,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Método que lida com outras exceptions genéricas, relaciondas a erros do
     * servidor.
     * 
     * @param exception Excessão que acabou de ser lançada
     * @param request   Dados da Requisição
     * @return Um ResponseEntity com erro código 500
     */
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Método que lida com exceptions relacionadas a erros de Regra de Negócio.
     * 
     * @param exception
     * @param request
     * @return Um ResponseEntity com o códio 409
     */
    public ResponseEntity<ErrorDetails> businessRuleExceptionHandler(BusinessRuleException exception,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

}
