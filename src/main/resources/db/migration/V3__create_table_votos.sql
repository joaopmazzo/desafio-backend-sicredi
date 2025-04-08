CREATE TABLE IF NOT EXISTS votos (
    id SERIAL PRIMARY KEY,
    sessao_id INT NOT NULL REFERENCES sessoes(id),
    associado_id INT NOT NULL,
    voto BIT NOT NULL,
    registrado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT UK_sessao_associado UNIQUE (sessao_id, associado_id)
);