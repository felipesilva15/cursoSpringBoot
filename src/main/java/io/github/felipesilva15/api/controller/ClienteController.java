package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.repository.Clientes;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {
    @Autowired
    private Clientes repository;

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    public Cliente getById (@PathVariable @ApiParam("ID do cliente") Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado!") );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salva um cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Cliente save ( @RequestBody @Valid Cliente cliente ) {
        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deleta um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cleinte deletado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
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
    @ApiOperation("Atualiza os dados de um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cleinte atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
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
    @ApiOperation("Buscar clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta realizada.")
    })
    public List<Cliente> find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
