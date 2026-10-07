package br.unipar.backend.minhaapi.controller;

import br.unipar.backend.minhaapi.model.Filme;
import br.unipar.backend.minhaapi.repository.FilmeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeRepository filmeRepository;

    public FilmeController(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    // Buscar Todos os Filmes
    @GetMapping
    public ResponseEntity<List<Filme>> listarFilmes() {
        return ResponseEntity.ok(filmeRepository.findAll());
    }

    // Buscar um Filme pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable int id) {
        return filmeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar um Novo Filme
    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        Filme filmeSalvo = filmeRepository.save(filme);
        return ResponseEntity.ok(filmeSalvo);
    }

    // Atualizar um Filme Via ID
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(
            @PathVariable int id,
            @RequestBody Filme filmeAtualizado) {

        return filmeRepository.findById(id)
                .map(filme -> {
                    filme.setTitulo(filmeAtualizado.getTitulo());
                    filme.setDiretor(filmeAtualizado.getDiretor());
                    filme.setGenero(filmeAtualizado.getGenero());
                    filme.setAno(filmeAtualizado.getAno());

                    filmeRepository.save(filme);

                    return ResponseEntity.ok(filme);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir um Filme Via ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFilme(@PathVariable int id) {

        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        filmeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}






