package io.github.felipesilva15.Service;

import io.github.felipesilva15.api.dto.PedidoDTO;
import io.github.felipesilva15.domain.entity.Pedido;

public interface PedidoService {
    Pedido save (PedidoDTO dto);
}
