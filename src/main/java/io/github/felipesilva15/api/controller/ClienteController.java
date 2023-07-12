package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private Clientes repository;

    @GetMapping("/{id}")
    public Cliente getById (@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado!") );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cliente save ( @RequestBody @Valid Cliente cliente ) {
        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete ( @PathVariable Integer id) {
        repository
                .findById(id)
                .map( cliente -> {
                    repository.delete(cliente);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado!") );
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update ( @PathVariable Integer id, @RequestBody @Valid Cliente cliente ) {
        repository
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado!") );
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
