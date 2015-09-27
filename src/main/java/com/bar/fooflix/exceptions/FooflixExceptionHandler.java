package com.bar.fooflix.exceptions;

import com.bar.fooflix.domain.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles all exceptions that are thrown back up to controllers.
 */
@ControllerAdvice
public class FooflixExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FooflixExceptionHandler.class);

    @ExceptionHandler({BindException.class, HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleBindException(BindException e) {
        LOG.error(e.getMessage(), e);
        ErrorMessage message = new ErrorMessage();
        message.setMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<?> handleThrowable(Throwable t) {
        //LOG.error(t.getMessage(), t);
        LOG.error(t.getMessage());
        ErrorMessage message = new ErrorMessage();
        message.setMessage(t.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
