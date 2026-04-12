package com.lionani07.algamoney_api.controller;

import com.lionani07.algamoney_api.exception.AlgamoneyResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String userMessage = getMessage("request.invalida");
        String developerMessage = getRootCause(ex);
        val erros = List.of(new ApiError(userMessage, developerMessage));
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        val erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ApiError.of(getMessage(fieldError), fieldError.toString()))
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(AlgamoneyResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(AlgamoneyResourceNotFoundException ex) {
        val errors =  List.of(new ApiError(ex.getMessage(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    private String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    private String getMessage(FieldError fieldError) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, locale);
    }

    private String getRootCause(Exception ex) {
        Throwable cause = ex.getCause();
        return cause != null ? cause.toString() : ex.toString();
    }

    @AllArgsConstructor
    @Getter
    public static class ApiError {
        private final String mensagemUsuario;
        private final String mensagemDesenvolvedor;

        public static ApiError of(String userMessage, String developerMessage) {
            return new ApiError(userMessage, developerMessage);
        }
    }
}