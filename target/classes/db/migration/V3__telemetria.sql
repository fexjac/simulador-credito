CREATE TABLE IF NOT EXISTS request_metric (
  id           BIGSERIAL PRIMARY KEY,
  method       VARCHAR(10)  NOT NULL,
  path         VARCHAR(255) NOT NULL,
  status       INT          NOT NULL,
  duration_ms  INT          NOT NULL,
  created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_req_metric_created ON request_metric(created_at);
CREATE INDEX IF NOT EXISTS idx_req_metric_path    ON request_metric(path);
