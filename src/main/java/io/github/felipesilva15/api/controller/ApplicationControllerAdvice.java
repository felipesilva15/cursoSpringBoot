package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.api.ApiErrors;
import io.github.felipesilva15.exception.PedidoNaoEncontradoExpection;
import io.github.felipesilva15.exception.RegraNegocioException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException (RegraNegocioException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoExpection.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePedidoNaoEncontradoExpection (PedidoNaoEncontradoExpection ex) {
        return new ApiErrors(ex.getMessage());
    }
}
