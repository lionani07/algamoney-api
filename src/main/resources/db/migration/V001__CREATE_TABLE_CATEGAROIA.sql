CREATE TABLE CATEGORIA (
    CODIGO BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(50) NOT NULL
) ENGINE=InnoDb DEFAULT CHARSET=utf8;

INSERT INTO CATEGORIA (NOME) VALUES
('Lazer'),
('Alimentação'),
('Supermercado'),
('Farmácia'),
('Outros');