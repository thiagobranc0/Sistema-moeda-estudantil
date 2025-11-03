CREATE TABLE IF NOT EXISTS departamento (
 id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
 nome VARCHAR(120) NOT NULL,
 instituicao_id BIGINT NOT NULL,
 CONSTRAINT fk_departamento_instituicao FOREIGN KEY (instituicao_id) REFERENCES instituicao(id)
);