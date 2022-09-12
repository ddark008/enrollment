package ru.ddark008.yadisk.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ddark008.yadisk.model.Error;

/**
 * Перехват ошибок и преобразование в ответы сервера
 */
@ControllerAdvice
public class ItemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ItemNotFoundException.class)
    protected ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException e, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Item not found");
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ItemValidationException.class)
    protected ResponseEntity<Object> handleItemValidationException(ItemValidationException e, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed");
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }
}
