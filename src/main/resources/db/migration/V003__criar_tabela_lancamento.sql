CREATE TABLE lancamento (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(50) NOT NULL,
data_vencimento DATE NOT NULL,
data_pagamento DATE,
valor DECIMAL(10,2) NOT NULL,
observacao VARCHAR(100),
tipo VARCHAR(20) NOT NULL,
codigo_categoria BIGINT(20) NOT NULL,
codigo_pessoa BIGINT(20) NOT NULL,
FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES
('Aluguel', '2024-07-10', '2024-07-10', 1500.00, 'Aluguel do apartamento', 'DESPESA', 5, 1),
('Salário', '2024-07-05', '2024-07-05', 5000.00, 'Salário mensal', 'RECEIRA', 5, 1),
('Supermercado', '2024-07-15', NULL, 300.00, 'Compras do mês', 'DESPESA', 3, 1),
('Farmácia', '2024-07-20', NULL, 50.00, 'Medicamentos', 'DESPESA', 4, 1),
('Cinema', '2024-07-25', NULL, 30.00, 'Ingresso para o cinema', 'DESPESA', 1, 1);