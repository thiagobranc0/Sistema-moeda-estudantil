INSERT INTO instituicao (nome)
VALUES ('Instituição Exemplo - Sistema Moeda Estudantil')
    ON CONFLICT (nome) DO NOTHING;

INSERT INTO departamento (nome, instituicao_id)
VALUES (
           'Departamento de Computação',
           (SELECT id FROM instituicao WHERE nome = 'Instituição Exemplo - Sistema Moeda Estudantil')
       );


INSERT INTO usuario (id, nome, email, tipo, senha)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Prof. Ada Lovelace',  'ada.prof@example.com',   'PROFESSOR', 'senha_ada'),
    ('22222222-2222-2222-2222-222222222222', 'Prof. Alan Turing',   'alan.prof@example.com',  'PROFESSOR', 'senha_alan'),
    ('33333333-3333-3333-3333-333333333333', 'Prof. Grace Hopper',  'grace.prof@example.com', 'PROFESSOR', 'senha_grace');

WITH dep AS (
    SELECT id
    FROM departamento
    WHERE nome = 'Departamento de Computação'
    LIMIT 1
    )
INSERT INTO professor (id_usuario, cpf, saldo, departamento_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', '00000000000001', 1000.00, (SELECT id FROM dep)),
    ('22222222-2222-2222-2222-222222222222', '00000000000002', 1000.00, (SELECT id FROM dep)),
    ('33333333-3333-3333-3333-333333333333', '00000000000003', 1000.00, (SELECT id FROM dep));
