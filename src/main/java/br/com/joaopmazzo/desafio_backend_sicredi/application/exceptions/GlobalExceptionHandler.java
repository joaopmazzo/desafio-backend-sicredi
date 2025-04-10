package br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions;

import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.BusinessException;
import br.com.joaopmazzo.desafio_backend_sicredi.application.exceptions.base.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedException(Exception ex) {
        log.error("Erro inesperado: {}", ex.getMessage(), ex);
        return buildErrorResponse("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Erro de recurso não encontrado: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        log.error("Erro de regra de negócio: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", message);
        errorResponse.put("status", status.value());

        return ResponseEntity.status(status).body(errorResponse);
    }

}
