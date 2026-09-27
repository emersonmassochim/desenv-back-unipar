package br.unipar.backend.minhaapi.controller;

import br.unipar.backend.minhaapi.model.Filme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private List<Filme> filmes = new ArrayList<>();
    private int id = 1;

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable int id) {

        for (Filme filme : filmes) {
            if (filme.getId() == id) {
                return ResponseEntity.ok(filme);
            }
        }


        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        filme.setId(id);
        id++;

        filmes.add(filme);

        return ResponseEntity.ok(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable int id, @RequestBody Filme filmeAtualizado) {

        for (Filme filme : filmes) {
            if (filme.getId() == id) {

                filme.setTitulo(filmeAtualizado.getTitulo());
                filme.setDiretor(filmeAtualizado.getDiretor());
                filme.setGenero(filmeAtualizado.getGenero());
                filme.setAno(filmeAtualizado.getAno());

                return ResponseEntity.ok(filme);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFilme(@PathVariable int id) {

        for (Filme filme : filmes) {
            if (filme.getId() == id) {
                filmes.remove(filme);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    //Pelo menos três filtros com @RequestParam;
    //Possibilidade de combinar filtros;
    @GetMapping("/filtro")
    public ResponseEntity<List<Filme>> filtrarFilmes(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String diretor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer ano) {

        List<Filme> resultado = new ArrayList<>();

        for (Filme filme : filmes) {

            boolean corresponde = true;

            if (titulo != null && !filme.getTitulo().equalsIgnoreCase(titulo)) {
                corresponde = false;
            }

            if (diretor != null && !filme.getDiretor().equalsIgnoreCase(diretor)) {
                corresponde = false;
            }

            if (genero != null && !filme.getGenero().equalsIgnoreCase(genero)) {
                corresponde = false;
            }

            if (ano != null && filme.getAno() != ano) {
                corresponde = false;
            }

            if (corresponde) {
                resultado.add(filme);
            }
        }

        return ResponseEntity.ok(resultado);
    }
}



