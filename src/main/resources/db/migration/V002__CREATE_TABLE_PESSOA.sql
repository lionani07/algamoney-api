CREATE TABLE pessoa (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo BIT(1) NOT NULL,
    logradouro VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(50),
    cep VARCHAR(10),
    cidade VARCHAR(50),
    estado VARCHAR(2)
)ENGINE=InnoDb DEFAULT CHARSET=utf8;

INSERT INTO pessoa (
    nome, ativo, logradouro,
    numero, complemento, cep,
    cidade, estado
)
VALUES(
    'Liorge Corria', 0, 'Rua josé vicente de barros',
    '2710', 'Apto 11B', '12061-001',
    'Taubaté', 'SP'
);

INSERT INTO pessoa (
    nome, ativo, logradouro,
    numero, complemento, cep,
    cidade, estado
)
VALUES(
    'Miriam Maria', 1, 'Rua josé vicente de barros',
    '2710', 'Apto 11B', '12061-001',
    'Taubaté', 'SP'
);

INSERT INTO pessoa (nome, ativo) VALUES('Irene Silva', 1);
INSERT INTO pessoa (nome, ativo) VALUES('Neide', 1);
INSERT INTO pessoa (nome, ativo) VALUES('Andreia', 1);