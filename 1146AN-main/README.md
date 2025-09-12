
## Como Executar

### 2. Executar a aplicação

#### Opção 1: Usando Maven
```bash
mvn spring-boot:run
```

#### Opção 2: Usando Maven Wrapper
```bash
./mvnw.cmd spring-boot:run
```

### 3. Verificar se a aplicação está rodando
A aplicação estará disponível em: `http://localhost:8080`

## Documentação da API com Swagger UI

Acessar:
**http://localhost:8080/swagger-ui/index.html**


### 2. Ou usar o Script no PowerShell

```powershell
cd 1146AN-main/authservice
./test-endpoints.ps1
```

## Configurações

### Banco de Dados H2
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:auth_db`
- **Username**: `sa`
- **Password**: `password`

### Configurações JWT
- **Secret**: Configurado em `application.properties`
- **Expiração**: Padrão do Spring Security

## Endpoints Disponíveis

### Autenticação
- `POST /auth/login/password` - Login com email e senha
- `POST /auth/refresh` - Renovar access token
- `POST /auth/logout` - Logout (revoga refresh token)

### Usuários
- `GET /users` - Listar usuários (com paginação)
- `POST /users` - Registrar novo usuário

### Teste
- `GET /hello` - Endpoint de teste simples


#
