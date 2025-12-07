# Sistema de Moeda Estudantil

Sistema de reconhecimento acadêmico que permite a distribuição de moedas virtuais por professores aos alunos, que podem ser trocadas por vantagens oferecidas por empresas parceiras.

## 🚀 Como Iniciar o Projeto

### Pré-requisitos

Certifique-se de ter instalado:

- **Java 21** ou superior
- **Maven** 3.6+
- **Node.js** 18+ e npm
- **PostgreSQL** 15+ (ou Docker para rodar via container)
- **Git**

### 1. Clone o Repositório

```bash
git clone https://github.com/Brunamark/Sistema-moeda-estudantil.git
cd Sistema-moeda-estudantil
```

### 2. Configurar e Iniciar o Backend

#### Opção A: Usando Docker (Recomendado)

```bash
cd sistema/backend
docker-compose up -d
```

Isso irá:
- Subir um container PostgreSQL na porta 5433
- Subir a aplicação Spring Boot na porta 8081

#### Opção B: Rodando Localmente

1. **Configure o PostgreSQL**
   - Crie um banco de dados chamado `sistemamoeda`
   - Usuário: `postgres`
   - Senha: `postgres`
   - Porta: `5433`

2. **Execute a aplicação**

```bash
cd sistema/backend
./mvnw clean install
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

📝 **Swagger UI**: `http://localhost:8080/swagger-ui.html`

### 3. Configurar e Iniciar o Frontend

```bash
cd sistema/frontend
npm install
npm run dev
```

O frontend estará disponível em: `http://localhost:5173`

## 🔧 Configurações Importantes

### Backend

O arquivo `sistema/backend/src/main/resources/application.yaml` contém as configurações do banco de dados e email. Ajuste conforme necessário:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/sistemamoeda
    username: postgres
    password: postgres
```

### ⚠️ Configuração de Email (Obrigatória para Notificações)

Para testar a funcionalidade de notificações por email, você **deve** configurar suas próprias credenciais de email no arquivo `sistema/backend/src/main/resources/application.yaml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: seu-email@gmail.com
    password: 'sua-senha-de-app'
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
```

**Para Gmail:**
1. Acesse sua conta Google
2. Ative a verificação em duas etapas
3. Gere uma "Senha de App" em: https://myaccount.google.com/apppasswords
4. Use essa senha de app no campo `password` (não use sua senha normal)

### Frontend

O frontend está configurado para se comunicar com o backend. Se necessário, ajuste a URL da API em `sistema/frontend/src/shared/service/api.ts`.

## 📦 Estrutura do Projeto

```
Sistema-moeda-estudantil/
├── sistema/
│   ├── backend/          # API Spring Boot
│   └── frontend/         # Interface React + TypeScript
└── diagramas/            # Diagramas UML do sistema
```

## 🛠️ Tecnologias Utilizadas

### Backend
- Spring Boot 3.1.6
- Java 21
- PostgreSQL
- Flyway (migrations)
- Swagger/OpenAPI

### Frontend
- React 19
- TypeScript
- Vite
- Material-UI (MUI)
- React Router
- Axios
- TanStack Query

## 📄 Licença

Este projeto foi desenvolvido como trabalho acadêmico.
