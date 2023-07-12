package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.api.ApiErrors;
import io.github.felipesilva15.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException (RegraNegocioException ex) {
        String message = ex.getMessage();

        return new ApiErrors(message);
    }
}
