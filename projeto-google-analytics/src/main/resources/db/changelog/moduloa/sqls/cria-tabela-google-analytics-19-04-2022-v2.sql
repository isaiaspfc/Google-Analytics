DROP TABLE IF EXISTS pesquisa CASCADE;
CREATE SEQUENCE IF NOT EXISTS codigo_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE pesquisa(
    codigo BIGINT                NOT NULL DEFAULT NEXTVAL ('codigo_seq'),
    url                          VARCHAR (255),
    modulo                       VARCHAR (255) ,
    funcionalidade               VARCHAR (255),
    complemento                  VARCHAR (255),
    duracao_sessoes              NUMERIC (8,2),
    media_sessoes                NUMERIC (8,2),
    tempo_pagina                 NUMERIC (8,2),
    tempo_medio_pagina           NUMERIC (8,2),
    numero_vizualizacoes         BIGINT,
    sessoes                      BIGINT,
    usuarios                     BIGINT,
    data_pesquisa                DATE,
    CONSTRAINT pesquisa_unique   UNIQUE (url, data_pesquisa),
    PRIMARY KEY (codigo)
)