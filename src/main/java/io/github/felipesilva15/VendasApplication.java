package io.github.felipesilva15;

import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.entity.Pedido;
import io.github.felipesilva15.domain.repository.Clientes;
import io.github.felipesilva15.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {
    @Bean
    public CommandLineRunner executar (@Autowired Clientes clientes, @Autowired Pedidos pedidos) {
        return args -> {
            Cliente cli = new Cliente("Felipe Silva");

            System.out.printf("*** Salvando clientes ***\n");
            clientes.save(cli);
            clientes.save(new Cliente("Roberto de Almeida"));

            Pedido novoPedido = new Pedido();
            novoPedido.setCliente(cli);
            novoPedido.setData(LocalDate.now());
            novoPedido.setTotal(BigDecimal.valueOf(200));

            pedidos.save(novoPedido);

            Cliente clienteComPedido = clientes.findClienteFetchPedidos(cli.getId());
            System.out.println(clienteComPedido);
            System.out.println(clienteComPedido.getPedidos());

            pedidos.findByCliente(clienteComPedido).forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}