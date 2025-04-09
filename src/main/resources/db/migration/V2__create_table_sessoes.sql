CREATE TABLE IF NOT EXISTS sessoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pauta_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    termino TIMESTAMP NOT NULL,

    CONSTRAINT FK_sessoes_pautas FOREIGN KEY (pauta_id) REFERENCES pautas(id),
    CONSTRAINT UK_pauta UNIQUE (pauta_id)
);