package algamoneyapi.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final var message = this.messageSource.getMessage("request.invalida", null, LocaleContextHolder.getLocale());
        final var messageDev = ex.toString();
        final var error = List.of(StandarError.of(message, messageDev));

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final var errors =  ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> FieldMessageError.of(
                        fieldError.getField(),
                        messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
                );

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        final var message = this.messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        final var error = StandarError.of(message, ex.toString());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest webRequest) {
        final var message = this.messageSource.getMessage("resource.operation-nao-permitida", null, LocaleContextHolder.getLocale());
        final var messageDev = ExceptionUtils.getRootCauseMessage(ex);
        final var error  = StandarError.of(message, messageDev);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
