# Simulador de Crédito – API (Java + Quarkus)

API REST para simulação de financiamento com **SAC** e **PRICE**, validação de produtos em **Azure SQL (somente leitura via JDBC)**, persistência em **PostgreSQL**, publicação de eventos no **Azure Event Hubs** e **telemetria** de chamadas.

---

## Sumário
- [Simulador de Crédito – API (Java + Quarkus)](#simulador-de-crédito--api-java--quarkus)
  - [Sumário](#sumário)
  - [Stack](#stack)
  - [Arquitetura (alto nível)](#arquitetura-alto-nível)
  - [Como rodar](#como-rodar)
    - [Docker (recomendado)](#docker-recomendado)
- [Postgres](#postgres)
- [MSSQL (Azure) – somente leitura](#mssql-azure--somente-leitura)
- [Event Hubs (não commitar segredo)](#event-hubs-não-commitar-segredo)
- [criar simulação](#criar-simulação)
- [listar](#listar)
- [volumes por dia/produto](#volumes-por-diaproduto)
- [telemetria](#telemetria)

---

## Stack
- **Java 21**, **Quarkus 3**
- **PostgreSQL** (persistência local via JPA/Hibernate + Flyway)
- **Azure SQL Server** (consulta de produtos via **JDBC** – *sem JPA*)
- **Azure Event Hubs** (publica o envelope da simulação)
- OpenAPI/Swagger UI (`/q/swagger-ui`), Health (`/q/health`)

---

## Arquitetura (alto nível)

1. **POST `/simulacoes`** recebe `{ valorDesejado, prazo }`.
2. Valida/seleciona **produto** consultando **dbo.PRODUTO** (Azure SQL via JDBC).
3. Calcula **SAC** e **PRICE**.
4. Persiste simulação/parcelas no **Postgres**.
5. Publica o envelope no **Event Hubs** (opcional, controlado por flag).
6. **GETs** expõem listagem, agregados por dia/produto e telemetria.

---

## Como rodar

### Docker (recomendado)

1) Crie um arquivo `.env` na raiz (exemplo **NÃO** commitar):
```env
# Postgres
PG_USER=postgres
PG_PASS=postgres
PG_DB=simulador

# MSSQL (Azure) – somente leitura
MSSQL_JDBC_URL=jdbc:sqlserver://dbhackathon.database.windows.net:1433;database=Hack;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
MSSQL_USERNAME=hack
MSSQL_PASSWORD=<SUA_SENHA>

# Event Hubs (não commitar segredo)
EVENTHUBS_CONN=Endpoint=sb://<namespace>.servicebus.windows.net/;SharedAccessKeyName=<policy>;SharedAccessKey=<SECRET>
EVENTHUBS_NAME=simulacoes
EVENTHUBS_ENABLED=true


Suba os serviços:

docker compose up -d --build
docker compose logs -f db api

ou mvn quarkus:dev

Endpoints
1) Criar simulação (POST /simulacoes)
2) Listar simulações (GET /simulacoes?pagina=1&qtdRegistrosPagina=200)
3) Volume por produto e por dia (GET /analytics/volumes-dia?data=YYYY-MM-DD)
4) Telemetria (GET /telemetria?data=YYYY-MM-DD)

# criar simulação
curl -X POST http://localhost:8080/simulacoes \
  -H "Content-Type: application/json" \
  -d '{"valorDesejado":900.00,"prazo":5}'

# listar
curl "http://localhost:8080/simulacoes?pagina=1&qtdRegistrosPagina=50"

# volumes por dia/produto
curl "http://localhost:8080/analytics/volumes-dia?data=2025-08-26"

# telemetria
curl "http://localhost:8080/telemetria?data=2025-08-26"
