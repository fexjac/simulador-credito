
CREATE TABLE IF NOT EXISTS produto_local (
  id              BIGSERIAL PRIMARY KEY,
  co_produto      INT NOT NULL UNIQUE,
  no_produto      VARCHAR(200) NOT NULL,
  pc_taxa_juros   NUMERIC(10,9) NOT NULL,
  nu_minimo_meses SMALLINT NOT NULL,
  nu_maximo_meses SMALLINT NULL,
  vr_minimo       NUMERIC(18,2) NOT NULL,
  vr_maximo       NUMERIC(18,2) NULL
);


CREATE TABLE IF NOT EXISTS simulacao (
  id               BIGSERIAL PRIMARY KEY,
  valor_desejado   NUMERIC(18,2) NOT NULL,
  prazo_meses      INT NOT NULL,
  codigo_produto   INT NOT NULL,
  descricao_produto VARCHAR(200) NOT NULL,
  taxa_juros       NUMERIC(10,9) NOT NULL,
  created_at       TIMESTAMP NOT NULL DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS parcela (
  id              BIGSERIAL PRIMARY KEY,
  simulacao_id    BIGINT NOT NULL REFERENCES simulacao(id) ON DELETE CASCADE,
  tipo            VARCHAR(10) NOT NULL,
  numero          INT NOT NULL,
  valor_amortizacao NUMERIC(18,2) NOT NULL,
  valor_juros     NUMERIC(18,2) NOT NULL,
  valor_prestacao NUMERIC(18,2) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_parcela_simulacao ON parcela(simulacao_id);
CREATE INDEX IF NOT EXISTS idx_simulacao_created ON simulacao(created_at);
CREATE INDEX IF NOT EXISTS idx_simulacao_codigo_produto ON simulacao(codigo_produto);
