# 🗳️ Microsserviço de Votação (Desafio Reality Show)

Este documento descreve a arquitetura e o funcionamento do sistema de votação assíncrona, desenvolvido como um desafio de backend, aplicando conceitos de microsserviços, mensageria e persistência de dados.

---

## 🔍 Visão Geral do Sistema

O sistema de votação é composto por dois **microsserviços principais** que se comunicam de forma **assíncrona** por meio de uma **fila de mensagens RabbitMQ**. O objetivo é permitir que usuários votem em um participante de reality show, garantindo a **unicidade do voto por CPF** e a **segurança dos dados**.

---

## 🧱 Componentes e Tecnologias

### 📌 Microsserviço da API de Votação

**Função:**  
Recebe as requisições de voto do cliente, realiza validações básicas e enfileira as mensagens no RabbitMQ para processamento posterior. Atua como o “porteiro” do sistema.

**Tecnologias:**
- Spring Boot (Java 21)
- Spring Web
- Spring AMQP

---

### ⚙️ Microsserviço Processador de Votos (Consumidor)

**Função:**  
Escuta a fila do RabbitMQ, processa as mensagens, valida a unicidade do CPF, criptografa dados sensíveis e persiste/atualiza os votos no banco de dados. É o “motor” do sistema.

**Tecnologias:**
- Spring Boot (Java 21)
- Spring AMQP
- Spring Data MongoDB
- Spring Security (PasswordEncoder, hashing)
- Lombok

---

### 🐇 RabbitMQ

**Função:**  
Atua como message broker, desacoplando a API do processador. Garante **resiliência**, evita **perda de mensagens** e possibilita **processamento assíncrono**.

---

### 🍃 MongoDB

**Função:**  
Banco de dados **NoSQL** utilizado para armazenar os registros de votos e dados sensíveis (devidamente **hasheados**).

---

## 🛠️ Outras Ferramentas e Conceitos

- **DTOs (Data Transfer Objects):**  
  Definem o contrato de dados entre a API e o consumidor via RabbitMQ.

- **Hashing (SHA-256):**  
  Criptografa CPF, e-mail e nome para garantir **anonimato** e permitir **validações de unicidade** sem armazenar dados sensíveis em texto puro.

- **Idempotência:**  
  A lógica no consumidor garante que, mesmo com múltiplos processamentos da mesma mensagem, o resultado final será o mesmo (voto atualizado, não duplicado).

- **Exceções Customizadas:**  
  Utilizadas para representar **erros de negócio específicos** (ex: CPF já votou), permitindo **tratamento adequado** nas camadas da aplicação.
