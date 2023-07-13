package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.service.PedidoService;
import io.github.felipesilva15.api.dto.AtualizacaoStatusPedidoDTO;
import io.github.felipesilva15.api.dto.InfoItemPedidoDTO;
import io.github.felipesilva15.api.dto.InfoPedidoDTO;
import io.github.felipesilva15.api.dto.PedidoDTO;
import io.github.felipesilva15.domain.entity.ItemPedido;
import io.github.felipesilva15.domain.entity.Pedido;
import io.github.felipesilva15.domain.enums.StatusPedido;
import io.github.felipesilva15.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private Pedidos repository;

    @Autowired
    private PedidoService service;

    @GetMapping("/{id}")
    public InfoPedidoDTO getById (@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map( pedido -> convertPedido(pedido) )
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado!") );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody @Valid PedidoDTO dto) {
        Pedido pedido = service.save(dto);

        return pedido.getId();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus (@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
        service.atualizaStatus(id, StatusPedido.valueOf(dto.getStatus()));
    }

    private InfoPedidoDTO convertPedido (Pedido pedido){
        return InfoPedidoDTO
                .builder()
                .id(pedido.getId())
                .dataPedido(pedido.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(convertItemPedido(pedido.getItens()))
                .build();
    }

    private List<InfoItemPedidoDTO> convertItemPedido(List<ItemPedido> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items
                .stream()
                .map( item -> InfoItemPedidoDTO
                        .builder()
                        .descricao(item.getProduto().getDescricao())
                        .quantidade(item.getQuantidade())
                        .precoUnitario(item.getPreco())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
