CREATE TABLE IF NOT EXISTS aluno (
 id_usuario UUID PRIMARY KEY,
 cpf VARCHAR(14),
 rg VARCHAR(20),
 endereco VARCHAR(255),
 saldo DECIMAL(19, 2) DEFAULT 0.00,
 CONSTRAINT uk_aluno_cpf UNIQUE (cpf),
 CONSTRAINT uk_aluno_rg UNIQUE (rg),
 CONSTRAINT fk_aluno_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);