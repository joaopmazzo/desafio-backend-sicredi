CREATE TABLE IF NOT EXISTS votos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sessao_id UUID NOT NULL REFERENCES sessoes(id),
    associado_id UUID NOT NULL,
    voto BIT NOT NULL,
    registrado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT UK_sessao_associado UNIQUE (sessao_id, associado_id)
);