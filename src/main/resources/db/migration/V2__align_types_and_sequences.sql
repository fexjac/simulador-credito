
ALTER TABLE produto_local
  ALTER COLUMN nu_minimo_meses TYPE INTEGER USING nu_minimo_meses::integer;

ALTER TABLE produto_local
  ALTER COLUMN nu_maximo_meses TYPE INTEGER USING nu_maximo_meses::integer;


DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'parcela_seq') THEN
        CREATE SEQUENCE parcela_SEQ START WITH 1 INCREMENT BY 50;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'produto_local_seq') THEN
        CREATE SEQUENCE produto_local_SEQ START WITH 1 INCREMENT BY 50;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'simulacao_seq') THEN
        CREATE SEQUENCE simulacao_SEQ START WITH 1 INCREMENT BY 50;
    END IF;
END$$;
