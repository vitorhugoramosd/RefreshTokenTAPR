# Script para testar os endpoints de autenticação

Write-Host "=== Testando Endpoints de Autenticação ===" -ForegroundColor Green

# 1. Testar Swagger UI
Write-Host "`n1. Testando acesso ao Swagger UI..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/swagger-ui/index.html" -Method GET
    Write-Host "✅ Swagger UI acessível: Status $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "❌ Erro ao acessar Swagger UI: $($_.Exception.Message)" -ForegroundColor Red
}

# 2. Testar registro de usuário
Write-Host "`n2. Testando registro de usuário..." -ForegroundColor Yellow
$registerBody = @{
    name = "Test User"
    email = "test@example.com"
    password = "password123"
    role = "USER"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/users" -Method POST -ContentType "application/json" -Body $registerBody
    Write-Host "✅ Usuário registrado: Status $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "❌ Erro ao registrar usuário: $($_.Exception.Message)" -ForegroundColor Red
}

# 3. Testar login
Write-Host "`n3. Testando login..." -ForegroundColor Yellow
$loginBody = @{
    email = "test@example.com"
    password = "password123"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/auth/login/password" -Method POST -ContentType "application/json" -Body $loginBody
    $loginResult = $response.Content | ConvertFrom-Json
    Write-Host "✅ Login realizado: Status $($response.StatusCode)" -ForegroundColor Green
    Write-Host "   Access Token: $($loginResult.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
    Write-Host "   Refresh Token: $($loginResult.refreshToken.Substring(0, 20))..." -ForegroundColor Cyan
    Write-Host "   Expires In: $($loginResult.expiresIn) segundos" -ForegroundColor Cyan
    
    # Salvar tokens para próximos testes
    $global:accessToken = $loginResult.accessToken
    $global:refreshToken = $loginResult.refreshToken
} catch {
    Write-Host "❌ Erro no login: $($_.Exception.Message)" -ForegroundColor Red
}

# 4. Testar refresh token
Write-Host "`n4. Testando refresh token..." -ForegroundColor Yellow
if ($global:refreshToken) {
    $refreshBody = @{
        refreshToken = $global:refreshToken
    } | ConvertTo-Json
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/auth/refresh" -Method POST -ContentType "application/json" -Body $refreshBody
        $refreshResult = $response.Content | ConvertFrom-Json
        Write-Host "✅ Token renovado: Status $($response.StatusCode)" -ForegroundColor Green
        Write-Host "   Novo Access Token: $($refreshResult.accessToken.Substring(0, 20))..." -ForegroundColor Cyan
        Write-Host "   Novo Refresh Token: $($refreshResult.refreshToken.Substring(0, 20))..." -ForegroundColor Cyan
        
        # Atualizar tokens
        $global:accessToken = $refreshResult.accessToken
        $global:refreshToken = $refreshResult.refreshToken
    } catch {
        Write-Host "❌ Erro no refresh: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "⚠️  Refresh token não disponível" -ForegroundColor Yellow
}

# 5. Testar logout
Write-Host "`n5. Testando logout..." -ForegroundColor Yellow
if ($global:refreshToken) {
    $logoutBody = @{
        refreshToken = $global:refreshToken
    } | ConvertTo-Json
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/auth/logout" -Method POST -ContentType "application/json" -Body $logoutBody
        Write-Host "✅ Logout realizado: Status $($response.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host "❌ Erro no logout: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "⚠️  Refresh token não disponível" -ForegroundColor Yellow
}

# 6. Testar refresh após logout (deve falhar)
Write-Host "`n6. Testando refresh após logout (deve falhar)..." -ForegroundColor Yellow
if ($global:refreshToken) {
    $refreshBody = @{
        refreshToken = $global:refreshToken
    } | ConvertTo-Json
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/auth/refresh" -Method POST -ContentType "application/json" -Body $refreshBody
        Write-Host "❌ Refresh deveria ter falhado após logout!" -ForegroundColor Red
    } catch {
        Write-Host "✅ Refresh falhou corretamente após logout: $($_.Exception.Message)" -ForegroundColor Green
    }
}

Write-Host "`n=== Teste Concluído ===" -ForegroundColor Green
