DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'request_metric_seq') THEN
        CREATE SEQUENCE request_metric_SEQ START WITH 1 INCREMENT BY 50;
    END IF;
END$$;
