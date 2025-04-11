CREATE TABLE IF NOT EXISTS associados (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    documento VARCHAR(14) NOT NULL,
    able_to_vote BIT NOT NULL,

    CONSTRAINT UK_documento UNIQUE (documento)
);

INSERT INTO associados (documento, able_to_vote) VALUES
 ('72661702067', B'1'),
 ('51437993095', B'1'),
 ('73331659061', B'1'),
 ('10312139020', B'0'),
 ('69286897065', B'1'),
 ('26989776048', B'0'),
 ('84018997027', B'1'),
 ('36690146081', B'0');