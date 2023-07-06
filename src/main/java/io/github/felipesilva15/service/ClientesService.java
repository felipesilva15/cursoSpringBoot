package io.github.felipesilva15.service;

import io.github.felipesilva15.model.Cliente;
import io.github.felipesilva15.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {
    private ClientesRepository repository;

    @Autowired
    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);

        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente) {
        // Valida os dados do cliente
    }
}