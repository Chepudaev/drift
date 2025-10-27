# 📸 Руководство по загрузке и обрезке изображений

## 🎯 Обзор

Реализована система загрузки изображений с возможностью обрезки и создания круглых аватарок, как в Instagram.

## 🏗️ Архитектура

### Backend

1. **ImageUploadController** - API для загрузки и обработки изображений
2. **ImageProcessingService** - Сервис для обработки изображений (crop, resize, circular)
3. **FileUploadConfig** - Конфигурация для раздачи загруженных файлов

### Frontend Demo

**Файл**: `src/main/resources/static/image-upload-demo.html`

Простая HTML страница с функционалом:
- Drag & Drop загрузка
- Предпросмотр круглого аватара
- Загрузка на сервер

## 🚀 API Endpoints

### 1. Загрузить изображение

**POST** `/api/upload/image`

**Параметры:**
- `file` (MultipartFile) - файл изображения

**Ответ:**
```json
{
  "url": "http://localhost:8080/api/files/filename.jpg",
  "filename": "filename.jpg"
}
```

### 2. Получить изображение

**GET** `/api/files/{filename}`

Возвращает изображение по имени файла.

### 3. Обрезать изображение

**POST** `/api/upload/crop`

**Параметры:**
- `file` (MultipartFile) - файл изображения
- `x` (int, optional) - координата X левого верхнего угла
- `y` (int, optional) - координата Y левого верхнего угла
- `width` (int) - ширина области обрезки
- `height` (int) - высота области обрезки
- `circular` (boolean, default: false) - создать круглое изображение

**Ответ:**
```json
{
  "url": "http://localhost:8080/api/files/filename_processed.jpg",
  "filename": "filename_processed.jpg"
}
```

### 4. Удалить изображение

**DELETE** `/api/files/{filename}`

## 📝 Использование

### Тестирование через HTML Demo

1. Запустите приложение Spring Boot
2. Откройте браузер и перейдите на: `http://localhost:8080/image-upload-demo.html`
3. Выберите изображение или перетащите его в область загрузки
4. Нажмите "Загрузить"
5. Получите URL для использования в вашем API

### Интеграция в ваше приложение

#### React Example (с использованием cropperjs)

```bash
npm install cropperjs
```

```jsx
import React, { useState } from 'react';
import Cropper from 'react-cropper';
import 'cropperjs/dist/cropper.css';

function ImageUpload() {
  const [image, setImage] = useState(null);
  const [cropper, setCropper] = useState(null);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => setImage(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const handleUpload = () => {
    if (cropper) {
      const canvas = cropper.getCroppedCanvas({
        width: 400,
        height: 400,
        imageSmoothingEnabled: true,
      });

      canvas.toBlob((blob) => {
        const formData = new FormData();
        formData.append('file', blob, 'avatar.png');

        fetch('http://localhost:8080/api/upload/image', {
          method: 'POST',
          body: formData,
        })
          .then(res => res.json())
          .then(data => console.log('URL:', data.url));
      });
    }
  };

  return (
    <div>
      <input type="file" onChange={handleFileChange} />
      {image && (
        <>
          <Cropper
            style={{ height: 400, width: '100%' }}
            aspectRatio={1}
            src={image}
            onInitialized={setCropper}
            guides={false}
          />
          <button onClick={handleUpload}>Загрузить</button>
        </>
      )}
    </div>
  );
}
```

#### Vue Example (с использованием vue-cropper)

```bash
npm install vue-cropper
```

```vue
<template>
  <div>
    <input type="file" @change="handleFileChange" />
    <vue-cropper
      v-if="image"
      ref="cropper"
      :src="image"
      :aspect-ratio="1"
      :guides="false"
    />
    <button @click="uploadImage">Загрузить</button>
  </div>
</template>

<script>
import 'vue-cropper/dist/index.css';
import VueCropper from 'vue-cropper';

export default {
  components: { VueCropper },
  data() {
    return {
      image: null,
    };
  },
  methods: {
    handleFileChange(e) {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.image = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    uploadImage() {
      this.$refs.cropper.getCroppedCanvas().toBlob((blob) => {
        const formData = new FormData();
        formData.append('file', blob, 'avatar.png');

        fetch('http://localhost:8080/api/upload/image', {
          method: 'POST',
          body: formData,
        })
          .then((res) => res.json())
          .then((data) => console.log('URL:', data.url));
      });
    },
  },
};
</script>
```

## ⚙️ Конфигурация

### application.properties

```properties
# Максимальный размер файла
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Директория для сохранения файлов
file.upload-dir=uploads

# Базовый URL сервера
server.base-url=http://localhost:8080
```

## 📦 Улучшения (будущие)

1. **Хранение в облаке** (AWS S3, Azure Blob):
   ```properties
   cloud.storage.type=s3
   cloud.storage.bucket=my-bucket
   ```

2. **Компрессия изображений** (tinify, imagemagick)

3. **Водяные знаки** для защиты контента

4. **Превью миниатюр** для быстрой загрузки

5. **CDN интеграция** для глобальной доставки

## 🔒 Безопасность

1. ✅ Валидация типов файлов
2. ✅ Ограничение размера файлов
3. ✅ Генерация уникальных имен файлов
4. ✅ Удаление неиспользуемых файлов

## 📖 Документация API

Полная документация доступна в Swagger UI:
`http://localhost:8080/swagger-ui.html`

## 🧪 Тестирование

### Postman

1. Откройте Postman
2. Выберите метод POST
3. URL: `http://localhost:8080/api/upload/image`
4. Body → form-data
5. Ключ: `file`, тип: File
6. Выберите изображение
7. Нажмите Send

### cURL

```bash
curl -X POST http://localhost:8080/api/upload/image \
  -F "file=@/path/to/image.jpg"
```

## 🐛 Устранение неполадок

### Ошибка: "MultipartException"

**Решение**: Увеличьте размер в `application.properties`:
```properties
spring.servlet.multipart.max-file-size=50MB
```

### Изображения не отображаются

**Решение**: Проверьте, что директория `uploads` существует и доступна для чтения.

### 403 Forbidden

**Решение**: Убедитесь, что `FileUploadConfig` правильно настроен для раздачи статических файлов.

## 📞 Поддержка

При возникновении проблем создайте issue в репозитории проекта.

