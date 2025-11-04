CREATE TABLE IF NOT EXISTS professor (
id_usuario UUID PRIMARY KEY,
cpf VARCHAR(14) NOT NULL,
saldo DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
departamento_id BIGINT NOT NULL,
CONSTRAINT uk_professor_cpf UNIQUE (cpf),
CONSTRAINT fk_professor_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
CONSTRAINT fk_professor_departamento FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);