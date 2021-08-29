CREATE TABLE lancamento (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    codigo_pessoa BIGINT(20) NOT NULL,
    codigo_categoria BIGINT(20) NOT NULL,
    descricao VARCHAR(50),
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10, 2) NOT NULL,
    observacao VARCHAR(100),
    tipo VARCHAR(20) NOT NULL,
    FOREIGN KEY(codigo_pessoa) REFERENCES pessoa(codigo),
    FOREIGN KEY(codigo_categoria) REFERENCES categoria(codigo)
)ENGINE=innoDb CHARSET=utf8;

INSERT INTO lancamento (
    codigo_pessoa, codigo_categoria, descricao,
    data_vencimento, data_pagamento, valor,
    observacao, tipo
)
VALUES (
    1, 1, 'Iphone XR Branco',
    '2021-02-10', null, 3200.00,
    'Prime Iphone', 'RECEITA'
);
