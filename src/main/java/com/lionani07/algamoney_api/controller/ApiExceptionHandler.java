package com.lionani07.algamoney_api.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        val mensagemUsuario = this.messageSource.getMessage("request.invalida", null, Locale.getDefault());
        val mensagemDesenvolvedor = ex.getCause().toString();

        return super.handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, status, request);
    }


    @AllArgsConstructor
    @Getter
    public static class Erro {
        private String mensagemUsuario;
        private String mensagemDesenvolvedor;
    }
}


