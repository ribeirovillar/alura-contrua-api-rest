package br.com.alura.forum.controller.config.validacao;

public class ErroRecursoNaoEncontradoDto {
    private String message;

    public ErroRecursoNaoEncontradoDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
