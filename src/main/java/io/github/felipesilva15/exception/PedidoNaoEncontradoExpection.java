package io.github.felipesilva15.exception;

public class PedidoNaoEncontradoExpection extends RuntimeException {
    public PedidoNaoEncontradoExpection() {
        super("Pedido não encontrado!");
    }
}
