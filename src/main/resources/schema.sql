CREATE DATABASE IF NOT EXISTS vendasspringbootapi;

USE vendasSpringBootApi;

CREATE TABLE IF NOT EXISTS cliente
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80),
    cpf VARCHAR(11)
);

CREATE TABLE IF NOT EXISTS produto
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(80),
    preuni NUMERIC(20,2)
);

CREATE TABLE IF NOT EXISTS pedido
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT REFERENCES cliente(id),
    data_pedido TIMESTAMP,
    total NUMERIC(20,2),
    status VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS item_pedido
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT REFERENCES peido(id),
    produto_id INT REFERENCES produto(id),
    preuni NUMERIC(20,2),
    quantidade INTEGER
);

CREATE TABLE IF NOT EXISTS usuario
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(50),
    senha VARCHAR(255),
    admin BOOL DEFAULT FALSE
)