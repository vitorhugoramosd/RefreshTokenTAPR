# Auth Service

Um serviço de autenticação robusto e seguro construído com Spring Boot, que oferece funcionalidades completas de autenticação e autorização usando JWT (JSON Web Tokens) e refresh tokens.

## 📋 Sobre o Sistema

O **Auth Service** é uma API REST que fornece um sistema completo de autenticação para aplicações web e mobile. O sistema implementa as melhores práticas de segurança, incluindo hash de senhas com BCrypt, tokens JWT para autenticação e refresh tokens para renovação segura de sessões.

### 🚀 Principais Funcionalidades

- **Registro de Usuários**: Criação de contas com validação de dados
- **Autenticação por Senha**: Login seguro com email e senha
- **Sistema de Tokens JWT**: Geração e validação de access tokens
- **Refresh Tokens**: Renovação automática de tokens de acesso
- **Logout Seguro**: Revogação de tokens e encerramento de sessões
- **Gerenciamento de Usuários**: Listagem e consulta de usuários cadastrados
- **Sistema de Roles**: Controle de permissões por tipo de usuário
- **Documentação API**: Interface Swagger/OpenAPI integrada

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **JWT (Auth0)** - Geração e validação de tokens
- **BCrypt** - Hash de senhas
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciamento de dependências

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture** e **Domain-Driven Design (DDD)**:

```
src/main/java/com/example/authservice/
├── application/          # Casos de uso e regras de negócio
│   ├── auth/            # Handlers de autenticação
│   ├── user/            # Handlers de usuário
│   └── port/            # Interfaces (contratos)
├── domain/              # Entidades e regras de domínio
│   ├── user/            # Entidade User e Value Objects
│   └── refreshtoken/    # Entidade RefreshToken
├── infrastructure/      # Implementações concretas
│   ├── config/          # Configurações
│   ├── persistence/     # Repositórios JPA
│   └── security/        # Implementações de segurança
└── interfaces/          # Camada de apresentação
    └── rest/            # Controllers REST e DTOs
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

### Instalação e Execução

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd 1146AN-main/authservice
   ```

2. **Execute o projeto**
   ```bash
   mvn spring-boot:run
   ```

3. **Acesse a aplicação**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console

### Configuração do Banco H2

- **URL**: `jdbc:h2:mem:auth_db`
- **Usuário**: `sa`
- **Senha**: `password`

## 📚 Documentação da API

### Endpoints Disponíveis

#### Autenticação (`/auth`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/login/password` | Login com email e senha |
| POST | `/auth/refresh` | Renovar access token |
| POST | `/auth/logout` | Logout e revogação de token |

#### Usuários (`/users`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/users` | Listar usuários (paginado) |
| POST | `/users` | Registrar novo usuário |

### Exemplos de Uso

#### 1. Registrar Usuário
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

#### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/auth/login/password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

#### 3. Renovar Token
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "seu-refresh-token-aqui"
  }'
```

## 🔐 Segurança

- **Hash de Senhas**: BCrypt com salt automático
- **JWT Tokens**: Assinatura HMAC-SHA256
- **Refresh Tokens**: Tokens seguros para renovação
- **Validação de Dados**: Validação robusta de entrada
- **CORS**: Configurado para segurança

## 🧪 Testes

Execute os testes unitários:

```bash
mvn test
```

## 📝 Configurações

As principais configurações podem ser ajustadas no arquivo `application.properties`:

```properties
# Porta do servidor
server.port=8080

# Configuração do banco H2
spring.datasource.url=jdbc:h2:mem:auth_db
spring.h2.console.enabled=true

# Configuração JWT
jwt.secret=UmaSenhaBemLongaParaNossaAplicacao
```

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Equipe de Desenvolvimento** - *Desenvolvimento inicial* - [SeuNome](https://github.com/seuusuario)

## 📞 Suporte

Para dúvidas ou suporte, entre em contato através de:
- Email: suporte@example.com
- Issues: [GitHub Issues](https://github.com/seuusuario/authservice/issues)

---

**Desenvolvido com ❤️ usando Spring Boot**
