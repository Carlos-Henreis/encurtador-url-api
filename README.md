# urlshort — Encurtador de URLs com QR Code e Estatísticas


[![Coverage Validation](https://github.com/Carlos-Henreis/encurtador-url-api/actions/workflows/validate-coverage.yml/badge.svg)](https://github.com/Carlos-Henreis/encurtador-url-api/actions/workflows/validate-coverage.yml)
[![Auto Create PR](https://github.com/Carlos-Henreis/encurtador-url-api/actions/workflows/auto-create-pr.yml/badge.svg)](https://github.com/Carlos-Henreis/encurtador-url-api/actions/workflows/auto-create-pr.yml)

Encurtador de URLs desenvolvido com **Java + Spring Boot**, seguindo o padrão de arquitetura **Hexagonal (Ports and Adapters)**.


Permite:

- Criar URLs curtas
- Redirecionar para a URL original
- Gerar QR Codes das URLs curtas
- Consultar estatísticas de acesso (último acesso e total de cliques)

---

## 📐 Arquitetura

O projeto segue o padrão **Hexagonal (Ports and Adapters)**:

```
Clientes (HTTP) 
       ↓
Adapter IN (Controller REST)
       ↓
Application (Domain + Service + Ports)
       ↓
Adapter OUT (JPA Repository + QR Code Generator)
       ↓
MySQL (Docker)
```

**Vantagens:**

- Baixo acoplamento
- Fácil para testes e manutenção
- Pronto para crescer (ex: adicionar outros bancos, filas, cache)

---

## 🚀 Como rodar localmente

### Pré-requisitos

- [Docker](https://www.docker.com/get-started/)
- [Java 17](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/install.html)

### 1. Subir o banco de dados

```bash
docker compose up -d
```

Isso criará:

- Banco Postgres `urlshort`
- Usuário: urlshort
- Senha: urlshort

### 2. Rodar o projeto

```bash
mvn spring-boot:run
```

A aplicação subirá em:

```
http://localhost:8080
```

---

## 📚 API (Swagger)

Documentação e testes diretamente no navegador:

```
http://localhost:8080/swagger-ui.html
```

Recursos disponíveis:

- POST `/` → Criar link curto
- GET `/{shortCode}` → Redirecionar para a URL original
- GET `/stats/{shortCode}` → Obter estatísticas
- GET `/qrcode/{shortCode}` → Obter QR Code

---

## 📊 Cobertura de Testes (Jacoco)

Após rodar:

```bash
mvn clean verify
```

Relatório de cobertura será gerado em:

```
target/site/jacoco/index.html
```

> 🚦 O build falhará caso a cobertura fique abaixo de 50%.

---

## 📦 Construído com

- Java 17
- Spring Boot
- Spring Data JPA
- Postgres (Docker)
- ZXing (QR Code Generator)
- Springdoc OpenAPI (Swagger)
- Jacoco (Test Coverage)
- GitHub Actions (CI/CD)


---

## 🧑‍💻 Autor

Carlos Henrique Reis  
[https://cahenre.com.br](https://cahenre.com.br)

---
