CREATE TABLE IF NOT EXISTS aluno (
 id_usuario UUID PRIMARY KEY,
 cpf VARCHAR(14) NOT NULL,
 rg VARCHAR(20) NOT NULL,
 endereco VARCHAR(255) NOT NULL,
 saldo DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
 CONSTRAINT uk_aluno_cpf UNIQUE (cpf),
 CONSTRAINT uk_aluno_rg UNIQUE (rg),
 CONSTRAINT fk_aluno_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);