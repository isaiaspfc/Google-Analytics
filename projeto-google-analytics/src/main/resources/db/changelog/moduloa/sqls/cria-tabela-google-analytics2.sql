DROP TABLE IF EXISTS pesquisa CASCADE;
CREATE SEQUENCE IF NOT EXISTS codigo_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE pesquisa(
    url VARCHAR (255) NULL,
    codigo BIGINT NULL DEFAULT NEXTVAL ('codigo_seq'),
    modulo VARCHAR (255) NULL,
    funcionalidade VARCHAR (255) NULL,
    complemento VARCHAR (255) NULL,
    numero_vizualizacoes BIGINT NULL,
    tempo_pagina NUMERIC (8,2) NULL,
    tempo_medio_pagina NUMERIC (8,2) NULL,
    sessoes BIGINT NULL,
    duracao_sessoes NUMERIC (8,2) NULL,
    media_sessoes NUMERIC (8,2) NULL,
    usuarios BIGINT NULL,
    data_pesquisa DATE NULL,
    CONSTRAINT pesquisa_unique UNIQUE (url, data_pesquisa),
    PRIMARY KEY (codigo)
)