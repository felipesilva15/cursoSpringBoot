package io.github.felipesilva15.Service.impl;

import io.github.felipesilva15.Service.PedidoService;
import io.github.felipesilva15.api.dto.ItemPedidoDTO;
import io.github.felipesilva15.api.dto.PedidoDTO;
import io.github.felipesilva15.domain.entity.Cliente;
import io.github.felipesilva15.domain.entity.ItemPedido;
import io.github.felipesilva15.domain.entity.Pedido;
import io.github.felipesilva15.domain.entity.Produto;
import io.github.felipesilva15.domain.enums.StatusPedido;
import io.github.felipesilva15.domain.repository.Clientes;
import io.github.felipesilva15.domain.repository.ItemsPedido;
import io.github.felipesilva15.domain.repository.Pedidos;
import io.github.felipesilva15.domain.repository.Produtos;
import io.github.felipesilva15.exception.PedidoNaoEncontradoExpection;
import io.github.felipesilva15.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private final Pedidos repository;

    @Autowired
    private final Clientes clientesRepository;

    @Autowired
    private final Produtos produtosRepository;

    @Autowired
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido save(PedidoDTO dto) {
        Cliente cliente = clientesRepository
                .findById(dto.getCliente())
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido!"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setData(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = convertItems(pedido, dto.getItems());

        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);

        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido status) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(status);
                    return repository.save(pedido);
                })
                .orElseThrow( () -> new PedidoNaoEncontradoExpection() );
    }

    private List<ItemPedido> convertItems (Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map( dto -> {
                    Produto produto = produtosRepository
                            .findById(dto.getProduto())
                            .orElseThrow( () -> new RegraNegocioException("Código de produto inválido! Código: " + dto.getProduto()));

                    ItemPedido item = new ItemPedido();

                    item.setQuantidade(dto.getQuantidade());
                    item.setPedido(pedido);
                    item.setProduto(produto);
                    item.setPreco(produto.getPreco());

                    return item;
                })
                .collect(Collectors.toList());
    }
}
