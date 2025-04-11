CREATE TABLE IF NOT EXISTS votos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sessao_id UUID NOT NULL,
    associado_id UUID NOT NULL,
    escolha_voto VARCHAR(3) NOT NULL,
    registrado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT FK_votos_sessoes FOREIGN KEY (sessao_id) REFERENCES sessoes(id),
    CONSTRAINT UK_sessao_associado UNIQUE (sessao_id, associado_id)
);