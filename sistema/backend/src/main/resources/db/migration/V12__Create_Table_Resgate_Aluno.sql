CREATE TABLE IF NOT EXISTS resgate_aluno (
                                             id UUID PRIMARY KEY,

                                             id_aluno    UUID NOT NULL,
                                             id_vantagem UUID NOT NULL,

                                             valor NUMERIC(19,2) NOT NULL,

    data_resgate TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_resgate_aluno
    FOREIGN KEY (id_aluno)
    REFERENCES aluno(id_usuario),

    CONSTRAINT fk_resgate_vantagem
    FOREIGN KEY (id_vantagem)
    REFERENCES vantagem(id)
    );

CREATE INDEX IF NOT EXISTS idx_resgate_aluno_aluno
    ON resgate_aluno(id_aluno);

CREATE INDEX IF NOT EXISTS idx_resgate_aluno_vantagem
    ON resgate_aluno(id_vantagem);

CREATE INDEX IF NOT EXISTS idx_resgate_aluno_data
    ON resgate_aluno(data_resgate);
