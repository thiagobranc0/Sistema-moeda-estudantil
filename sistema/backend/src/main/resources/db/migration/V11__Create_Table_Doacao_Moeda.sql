
CREATE TABLE IF NOT EXISTS doacao_moeda (
                                            id UUID PRIMARY KEY,

                                            id_professor UUID NOT NULL,
                                            id_aluno UUID NOT NULL,

                                            valor DECIMAL(19,2) NOT NULL,
    mensagem TEXT,
    data_doacao TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_doacao_professor
    FOREIGN KEY (id_professor)
    REFERENCES professor(id_usuario),

    CONSTRAINT fk_doacao_aluno
    FOREIGN KEY (id_aluno)
    REFERENCES aluno(id_usuario)
    );

-- Índices para desempenho
CREATE INDEX IF NOT EXISTS idx_doacao_professor ON doacao_moeda(id_professor);
CREATE INDEX IF NOT EXISTS idx_doacao_aluno ON doacao_moeda(id_aluno);
CREATE INDEX IF NOT EXISTS idx_doacao_data ON doacao_moeda(data_doacao);
