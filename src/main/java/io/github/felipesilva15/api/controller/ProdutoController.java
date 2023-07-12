package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.domain.entity.Produto;
import io.github.felipesilva15.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private Produtos repository;

    @GetMapping("/{id}")
    public Produto getById (@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado!") );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save (@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete (@PathVariable Integer id) {
        repository
                .findById(id)
                .map(produto -> {
                    repository.delete(produto);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado!") );
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update (@PathVariable Integer id, @RequestBody Produto produto) {
        repository
                .findById(id)
                .map( produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    repository.save(produto);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado!") );
    }

    @GetMapping
    public List<Produto> find (Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
