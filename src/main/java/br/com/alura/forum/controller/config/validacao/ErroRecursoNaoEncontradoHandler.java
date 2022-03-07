package br.com.alura.forum.controller.config.validacao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErroRecursoNaoEncontradoHandler {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchElementException.class)
    public ErroRecursoNaoEncontradoDto handler (NoSuchElementException exception) {
        return new ErroRecursoNaoEncontradoDto(exception.getMessage());
    }

}
