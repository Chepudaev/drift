# JWT Authentication - Руководство по использованию

## Обзор

В вашем приложении теперь реализована JWT (JSON Web Token) аутентификация. Это означает, что вместо сессионной аутентификации используется stateless подход с токенами.

## Основные изменения

### 1. Новые зависимости
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` - для работы с JWT токенами

### 2. Новые компоненты
- `JwtProperties` - конфигурация JWT
- `JwtUtils` - утилиты для работы с токенами
- `JwtService` - сервис для аутентификации и генерации токенов
- `JwtAuthenticationFilter` - фильтр для проверки JWT токенов
- `RefreshTokenRequest` - DTO для refresh token запросов

### 3. Обновленные компоненты
- `SecurityConfig` - настроен для stateless аутентификации
- `AuthController` - добавлены endpoints для работы с JWT
- `LoginResponse` - добавлены поля для токенов

## API Endpoints

### 1. Логин
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "your_username",
    "password": "your_password"
}
```

**Ответ:**
```json
{
    "userId": 1,
    "username": "your_username",
    "roles": ["ROLE_USER"],
    "message": "Успешная аутентификация",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 2. Обновление токенов
```http
POST /api/auth/refresh
Content-Type: application/json

{
    "refreshToken": "your_refresh_token"
}
```

### 3. Получение информации о текущем пользователе
```http
GET /api/auth/me
Authorization: Bearer your_access_token
```

### 4. Регистрация
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "new_user",
    "password": "new_password"
}
```

## Использование токенов

### Access Token
- **Время жизни:** 24 часа (настраивается в `application.properties`)
- **Использование:** Добавляйте в заголовок `Authorization: Bearer <access_token>`
- **Назначение:** Аутентификация для всех защищенных endpoints

### Refresh Token
- **Время жизни:** 7 дней (настраивается в `application.properties`)
- **Использование:** Для получения новых access token без повторного логина
- **Назначение:** Обновление access token

## Конфигурация

Настройки JWT в `application.properties`:

```properties
# JWT Configuration
jwt.secret=mySecretKey123456789012345678901234567890123456789012345678901234567890
jwt.expiration=86400000                    # 24 часа в миллисекундах
jwt.refresh-expiration=604800000           # 7 дней в миллисекундах
jwt.header-name=Authorization              # Название заголовка
jwt.token-prefix=Bearer                    # Префикс токена
```

## Пример использования в клиенте

### JavaScript/Frontend
```javascript
// Логин
const loginResponse = await fetch('/api/auth/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        username: 'your_username',
        password: 'your_password'
    })
});

const { accessToken, refreshToken } = await loginResponse.json();

// Сохранение токенов
localStorage.setItem('accessToken', accessToken);
localStorage.setItem('refreshToken', refreshToken);

// Использование токена для запросов
const apiResponse = await fetch('/api/users', {
    headers: {
        'Authorization': `Bearer ${accessToken}`
    }
});

// Обновление токена
const refreshResponse = await fetch('/api/auth/refresh', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        refreshToken: localStorage.getItem('refreshToken')
    })
});

const { accessToken: newAccessToken } = await refreshResponse.json();
localStorage.setItem('accessToken', newAccessToken);
```

### Postman
1. **Логин:** POST `/api/auth/login` с телом запроса
2. **Сохранение токена:** Скопируйте `accessToken` из ответа
3. **Использование:** Добавьте заголовок `Authorization: Bearer <token>` к запросам
4. **Обновление:** POST `/api/auth/refresh` с `refreshToken`

## Безопасность

1. **Секретный ключ:** Используйте сложный секретный ключ в production
2. **HTTPS:** Всегда используйте HTTPS в production
3. **Хранение токенов:** 
   - Access token: в памяти или localStorage (для SPA)
   - Refresh token: в httpOnly cookie (рекомендуется)
4. **Время жизни:** Настройте подходящее время жизни токенов

## Преимущества JWT

1. **Stateless:** Не требует хранения сессий на сервере
2. **Масштабируемость:** Легко масштабируется на несколько серверов
3. **Кроссплатформенность:** Работает с любыми клиентами
4. **Информативность:** Токен содержит информацию о пользователе

## Миграция с сессионной аутентификации

Ваше приложение теперь использует stateless JWT аутентификацию вместо сессионной. Это означает:
- Нет необходимости в сессиях
- Клиент должен хранить и передавать токены
- Токены имеют ограниченное время жизни
- Refresh token позволяет обновлять access token без повторного логина
