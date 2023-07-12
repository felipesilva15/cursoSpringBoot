package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private Clientes clientes;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getClienteById (@PathVariable Integer id) {
        Optional<Cliente> cliente =  clientes.findById(id);

        if (!cliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente.get());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save ( @RequestBody Cliente cliente ) {
        Cliente novoCliente = clientes.save(cliente);

        return ResponseEntity.ok(novoCliente);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete ( @PathVariable Integer id) {
        Optional<Cliente> cliente =  clientes.findById(id);

        if (!cliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        clientes.delete(cliente.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update ( @PathVariable Integer id, @RequestBody Cliente cliente ) {
        return clientes.findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);

                    return ResponseEntity.noContent().build();
                })
                .orElseGet( () -> {
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        List<Cliente> listaClientes = clientes.findAll(example);

        return ResponseEntity.ok(listaClientes);
    }
}
