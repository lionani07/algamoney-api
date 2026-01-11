CREATE TABLE pessoa(
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
ativo BOOLEAN NOT NULL,
logradouro VARCHAR(30) NOT NULL,
numero VARCHAR(30) NOT NULL,
complemento VARCHAR(50),
bairro VARCHAR(30),
cep VARCHAR(50),
cidade VARCHAR(30),
estado VARCHAR(30)
)ENGINE=InnoDB, CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
VALUES ('Liorge Corria', true, 'Rua Mariano Moreira', '79', 'Casa', 'Jd Naçoes', '12030-030', 'Taubate', 'SP');