package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.Service.PedidoService;
import io.github.felipesilva15.api.dto.PedidoDTO;
import io.github.felipesilva15.domain.entity.Pedido;
import io.github.felipesilva15.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private Pedidos repository;

    @Autowired
    private PedidoService service;

    @GetMapping("/{id}")
    public Pedido getById (@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!") );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody PedidoDTO dto) {
        Pedido pedido = service.save(dto);

        return pedido.getId();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete (@PathVariable Integer id) {
        repository
                .findById(id)
                .map( pedido -> {
                    repository.delete(pedido);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!") );
    }

    public void update (@PathVariable Integer id, @RequestBody Pedido pedido) {
        repository
                .findById(id)
                .map( pedidoExistente -> {
                    pedido.setId(pedidoExistente.getId());
                    repository.save(pedido);

                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!") );
    }


}
