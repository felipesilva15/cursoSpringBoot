CREATE TABLE cliente
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80)
);

CREATE TABLE produto
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(80),
    preuni NUMERIC(20,2)
);

CREATE TABLE PEDIDO
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT REFERENCES cliente(id),
    data_pedido TIMESTAMP,
    total NUMERIC(20,2)
);

CREATE TABLE ITEM_PEDIDO
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT REFERENCES peido(id),
    produto_id INT REFERENCES produto(id),
    preuni NUMERIC(20,2),
    quantidade INTEGER
);