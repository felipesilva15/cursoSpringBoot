package io.github.felipesilva15.Service;

import io.github.felipesilva15.api.dto.PedidoDTO;
import io.github.felipesilva15.domain.entity.Pedido;
import io.github.felipesilva15.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido save (PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido status);
}
