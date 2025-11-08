-- Ative gerador de UUID no Postgres (uma vez por banco)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1) Adicione a coluna sombra
ALTER TABLE vantagem
    ADD COLUMN id_uuid UUID DEFAULT gen_random_uuid() NOT NULL;

-- 2) Promova a coluna sombra a PK
ALTER TABLE vantagem DROP CONSTRAINT IF EXISTS vantagem_pkey;
ALTER TABLE vantagem ADD CONSTRAINT vantagem_pkey PRIMARY KEY (id_uuid);

-- 3) Remova a coluna antiga e renomeie a nova para 'id'
ALTER TABLE vantagem DROP COLUMN id;
ALTER TABLE vantagem RENAME COLUMN id_uuid TO id;

-- 4) Garanta default no banco (opcional, mas recomendável)
ALTER TABLE vantagem ALTER COLUMN id SET DEFAULT gen_random_uuid();
