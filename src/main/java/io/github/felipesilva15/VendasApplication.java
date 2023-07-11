package io.github.felipesilva15;

import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {
    @Bean
    public CommandLineRunner executar (@Autowired Clientes clientes) {
        return args -> {
            System.out.printf("*** Salvando clientes ***");
            clientes.save(new Cliente("Felipe Silva"));
            clientes.save(new Cliente("Roberto de Almeida"));

            List<Cliente> listaCliente = clientes.getAll();
            listaCliente.forEach(System.out::println);

            System.out.printf("\n*** Atualizando clientes ***\n");
            listaCliente.forEach(cliente -> {
                cliente.setNome(cliente.getNome() + " atualizado!");
                clientes.update(cliente);
            });

            listaCliente = clientes.getAll();
            listaCliente.forEach(System.out::println);

            System.out.printf("\n*** Buscando clientes por nome ***\n");
            clientes.getByName("Fel").forEach(System.out::println);

//            System.out.printf("\n*** Deletando clientes ***\n");
//            clientes.getAll().forEach(cliente -> {
//                clientes.delete(cliente);
//            });
//
//            listaCliente = clientes.getAll();
//            listaCliente.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}