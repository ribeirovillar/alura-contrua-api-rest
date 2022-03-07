package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> getTopicos(String nomeCurso) {

        if (Objects.isNull(nomeCurso)) {
            return TopicoDto.converter(topicoRepository.findAll());
        }
        return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso));

    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> createTopico (@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> getDetalhesTopico(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new DetalhesTopicoDto(optional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> updateTopico(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm atualizacaoTopicoForm) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = atualizacaoTopicoForm.atualizar(optional.get());
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTopico(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
