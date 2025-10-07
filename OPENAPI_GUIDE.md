# OpenAPI / Swagger UI - Руководство пользователя

## Обзор

Проект Drift Event Management API полностью документирован с использованием **OpenAPI 3.0** (ранее известного как Swagger). Это позволяет разработчикам легко изучать и тестировать API прямо из браузера.

## Доступ к документации

После запуска приложения, документация API доступна по следующим адресам:

### Swagger UI (интерактивная документация)
```
http://localhost:8080/swagger-ui.html
```

Swagger UI предоставляет интерактивный интерфейс, где вы можете:
- Просмотреть все доступные эндпоинты
- Изучить схемы запросов и ответов
- Выполнить тестовые запросы прямо из браузера
- Тестировать аутентификацию с JWT токенами

### OpenAPI спецификация (JSON)
```
http://localhost:8080/api-docs
```

### OpenAPI спецификация (YAML)
```
http://localhost:8080/api-docs.yaml
```

## Основные возможности

### 1. Группировка эндпоинтов по категориям

API организован в следующие категории:

- **Аутентификация** - Вход, регистрация, обновление токенов
- **Пользователи** - Управление пользователями
- **Автомобили** - Управление автомобилями
- **События** - Управление дрифт-событиями
- **Треки** - Управление треками и их конфигурациями
- **Расписания** - Управление расписаниями событий
- **Элементы расписания** - Управление элементами расписаний
- **Раунды** - Управление раундами соревнований
- **Face-to-Face соревнования** - Управление прямыми соревнованиями

### 2. Аутентификация в Swagger UI

Большинство эндпоинтов требуют JWT аутентификацию. Для тестирования:

1. Откройте Swagger UI: `http://localhost:8080/swagger-ui.html`

2. Найдите раздел **Аутентификация** и используйте эндпоинт `POST /api/auth/login` или `POST /api/auth/register`

3. Выполните запрос и скопируйте полученный `accessToken` из ответа

4. Нажмите кнопку **Authorize** (🔒) в верхней части страницы

5. В появившемся окне введите:
   ```
   Bearer <ваш_accessToken>
   ```
   Например:
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYzOTU...
   ```

6. Нажмите **Authorize**, затем **Close**

7. Теперь все последующие запросы будут автоматически включать JWT токен

### 3. Тестирование эндпоинтов

Для каждого эндпоинта вы можете:

1. Нажать на эндпоинт, чтобы развернуть его
2. Нажать **Try it out**
3. Заполнить необходимые параметры или тело запроса
4. Нажать **Execute**
5. Просмотреть ответ сервера, включая код состояния, заголовки и тело ответа

### 4. Просмотр схем данных

В нижней части Swagger UI находится раздел **Schemas**, где можно посмотреть структуру всех DTO:

- `LoginRequest` - Данные для входа
- `LoginResponse` - Ответ после входа (включает токены)
- `UserDto` - Информация о пользователе
- `CarDto` - Информация об автомобиле
- `EventDto` - Информация о событии
- `TrackDto` - Информация о треке
- И многие другие...

## Примеры использования

### Пример 1: Регистрация и получение токена

1. Раскройте раздел **Аутентификация**
2. Откройте `POST /api/auth/register`
3. Нажмите **Try it out**
4. Заполните тело запроса:
   ```json
   {
     "username": "testuser",
     "password": "password123"
   }
   ```
5. Нажмите **Execute**
6. Скопируйте `accessToken` из ответа

### Пример 2: Создание автомобиля

1. Авторизуйтесь (см. раздел "Аутентификация в Swagger UI")
2. Раскройте раздел **Автомобили**
3. Откройте `POST /api/cars`
4. Нажмите **Try it out**
5. Заполните тело запроса:
   ```json
   {
     "brand": "Toyota",
     "model": "Supra",
     "horsepower": 500,
     "year": 1998,
     "color1": "#FF0000",
     "userId": 1
   }
   ```
6. Нажмите **Execute**

### Пример 3: Получение списка событий

1. Авторизуйтесь
2. Раскройте раздел **События**
3. Откройте `GET /api/events`
4. Нажмите **Try it out**
5. Нажмите **Execute**
6. Просмотрите список всех событий

## Экспорт спецификации

Вы можете экспортировать OpenAPI спецификацию для использования в других инструментах:

### Postman
1. Откройте `http://localhost:8080/api-docs`
2. Скопируйте JSON
3. В Postman: Import → Raw text → Вставьте JSON

### Insomnia
1. Откройте `http://localhost:8080/api-docs`
2. Скопируйте JSON
3. В Insomnia: Import/Export → Import Data → From URL → Вставьте `http://localhost:8080/api-docs`

### Генерация клиентского кода

Используйте OpenAPI Generator для создания клиентских библиотек:

```bash
# Установка OpenAPI Generator
npm install @openapitools/openapi-generator-cli -g

# Генерация JavaScript клиента
openapi-generator-cli generate -i http://localhost:8080/api-docs \
  -g javascript \
  -o ./drift-api-client

# Генерация TypeScript/Angular клиента
openapi-generator-cli generate -i http://localhost:8080/api-docs \
  -g typescript-angular \
  -o ./drift-api-client-angular

# Генерация Java клиента
openapi-generator-cli generate -i http://localhost:8080/api-docs \
  -g java \
  -o ./drift-api-client-java
```

## Настройка

### Конфигурация в application.properties

Вы можете настроить OpenAPI в `src/main/resources/application.properties`:

```properties
# Путь к OpenAPI JSON
springdoc.api-docs.path=/api-docs

# Путь к Swagger UI
springdoc.swagger-ui.path=/swagger-ui.html

# Включение Swagger UI
springdoc.swagger-ui.enabled=true

# Сортировка операций по методу
springdoc.swagger-ui.operations-sorter=method

# Сортировка тегов по алфавиту
springdoc.swagger-ui.tags-sorter=alpha

# Включение кнопки "Try it out" по умолчанию
springdoc.swagger-ui.try-it-out-enabled=true

# URL серверов
drift.openapi.dev-url=http://localhost:8080
drift.openapi.prod-url=https://api.drift.example.com
```

### Изменение информации об API

Отредактируйте `src/main/java/com/example/drift/config/OpenApiConfig.java`:

```java
Info info = new Info()
    .title("Drift Event Management API")
    .version("1.0.0")
    .contact(contact)
    .description("Ваше описание API")
    .license(license);
```

## Безопасность

### JWT токены

API использует JWT (JSON Web Tokens) для аутентификации:

- **Access Token** - действителен 24 часа, используется для доступа к защищенным эндпоинтам
- **Refresh Token** - действителен 7 дней, используется для обновления access token

### Обновление токена

Когда access token истекает:

1. Используйте эндпоинт `POST /api/auth/refresh`
2. Передайте refresh token в теле запроса:
   ```json
   {
     "refreshToken": "ваш_refresh_token"
   }
   ```
3. Получите новые access и refresh токены

## Роли и права доступа

API использует следующие роли:

- **ROLE_USER** - базовая роль для всех пользователей
  - Доступ ко всем GET эндпоинтам
  
- **ROLE_JUDGE** - роль судьи
  - Все права ROLE_USER
  - Создание раундов (`POST /api/rounds`)
  - Обновление Face-to-Face соревнований (`PATCH /api/face-to-face/{id}`)
  
- **ROLE_MANAGER** - роль менеджера
  - Полный доступ ко всем эндпоинтам
  
- **ROLE_ADMIN** - роль администратора
  - Полный доступ ко всем эндпоинтам

## Коды ответов HTTP

- **200 OK** - Успешный запрос
- **201 Created** - Ресурс успешно создан
- **204 No Content** - Успешное удаление
- **400 Bad Request** - Неверные данные в запросе
- **401 Unauthorized** - Требуется аутентификация или токен недействителен
- **403 Forbidden** - Недостаточно прав для выполнения операции
- **404 Not Found** - Ресурс не найден
- **500 Internal Server Error** - Внутренняя ошибка сервера

## Дополнительные ресурсы

- [OpenAPI Specification](https://swagger.io/specification/)
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [OpenAPI Generator](https://openapi-generator.tech/)

## Поддержка

Если у вас возникли вопросы или проблемы с API, обратитесь:
- Email: support@drift.example.com
- Документация проекта: См. README.md и JWT_USAGE.md

