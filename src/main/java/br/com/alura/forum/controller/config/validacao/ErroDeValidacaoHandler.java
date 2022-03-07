package br.com.alura.forum.controller.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> handler(MethodArgumentNotValidException exception) {
        List<ErroDeFormularioDto> erroDeFormularioDtos = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erroDeFormularioDto = new ErroDeFormularioDto(fieldError.getField(), mensagem);
            erroDeFormularioDtos.add(erroDeFormularioDto);
        });
        return erroDeFormularioDtos;
    }
}
