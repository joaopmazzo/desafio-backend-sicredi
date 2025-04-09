ALTER TABLE votos DROP CONSTRAINT UK_sessao_associado;

ALTER TABLE votos
    ADD CONSTRAINT FK_votos_associados
        FOREIGN KEY (associado_id) REFERENCES associados(id);

ALTER TABLE votos
    ADD CONSTRAINT UK_sessao_associado UNIQUE (sessao_id, associado_id);