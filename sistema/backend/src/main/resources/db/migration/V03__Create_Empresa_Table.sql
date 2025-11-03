CREATE TABLE IF NOT EXISTS empresa (
 id_usuario UUID PRIMARY KEY,
 cnpj VARCHAR(18) NOT NULL,
 CONSTRAINT uk_empresa_cnpj UNIQUE (cnpj),
 CONSTRAINT fk_empresa_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);