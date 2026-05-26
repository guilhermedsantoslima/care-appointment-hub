<h1 align="center">🩺 Care Appointment Hub</h1>

<p align="center">
Sistema de agendamento médico desenvolvido com Java e Spring Boot
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0-green" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue" />
  <img src="https://img.shields.io/badge/RabbitMQ-Messaging-orange" />
  <img src="https://img.shields.io/badge/GraphQL-API-pink" />
  <img src="https://img.shields.io/badge/Docker-Container-blue" />
</p>

---

# 📌 Descrição do projeto

Projeto desenvolvido para o Tech Challenge da pós-graduação em Arquitetura e Desenvolvimento Java.

A aplicação foi construída utilizando Clean Architecture, autenticação JWT, GraphQL, RabbitMQ e microsserviços, promovendo separação de responsabilidades, baixo acoplamento e escalabilidade.

---

# 🚀 Funcionalidades

✅ CRUD de usuários  
✅ Login com JWT  
✅ Controle de acesso por perfil  
✅ CRUD de consultas médicas  
✅ GraphQL para consultas  
✅ RabbitMQ para mensageria  
✅ Microsserviço de notificações  
✅ Docker e Docker Compose  
✅ Testes unitários  

---

# 🛠️ Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- RabbitMQ
- GraphQL
- Docker
- JUnit 5
- Mockito
- Swagger/OpenAPI

---

# 🐳 Guia de implantação

## Clone o repositório

```bash
git clone https://github.com/guilhermedsantoslima/care-appointment-hub.git
```

---

## Execute a aplicação

```bash
docker compose up --build
```

---

# 📚 Documentação

## Swagger

```text
http://localhost:8081/swagger-ui/index.html
```

---

## GraphQL

```text
http://localhost:8081/graphql
```

---

# 🔑 Usuário administrador padrão

```json
{
  "email": "admin@admin.com",
  "password": "123456"
}
```

---

# 🔄 Microsserviços

## 🔹 care-appointment-hub

Responsável por:
- autenticação
- gerenciamento de usuários
- consultas médicas
- GraphQL
- regras de negócio

## 🔹 notification-service

Responsável por:
- consumo de eventos RabbitMQ
- processamento de notificações

---

# 📂 Estrutura do projeto

```text
care-appointment-hub
│
├── care-appointment-hub
│
├── notification-service
│
└── docker-compose.yml
```

---

# 👨‍💻 Desenvolvedor

Guilherme Lima

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Perfil-blue?logo=linkedin)](https://linkedin.com/in/guilherme-lima-007075243)
[![GitHub](https://img.shields.io/badge/GitHub-Repositório-black?logo=github)](https://github.com/guilhermedsantoslima)
