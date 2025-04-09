CREATE TABLE IF NOT EXISTS associados (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    documento VARCHAR(14) NOT NULL,
    able_to_vote BIT NOT NULL,

    CONSTRAINT UK_documento UNIQUE (documento)
);

INSERT INTO associados (documento, able_to_vote) VALUES
('12345678901', B'1'),
('98765432100', B'0'),
('12345678000195', B'1'),
('98765432000100', B'0'),
('11122233344', B'1'),
('55566677788', B'0'),
('11222333000181', B'1'),
('99887766000199', B'0');