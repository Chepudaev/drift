package com.example.drift.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ImageProcessingService {

    /**
     * Обрезает изображение по заданным координатам и размерам
     * 
     * @param imageBytes байты изображения
     * @param x координата X левого верхнего угла
     * @param y координата Y левого верхнего угла
     * @param width ширина области обрезки
     * @param height высота области обрезки
     * @return обрезанное изображение в виде байтов
     */
    public byte[] cropImage(byte[] imageBytes, int x, int y, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        
        // Проверка границ
        if (x < 0 || y < 0 || x + width > originalImage.getWidth() || y + height > originalImage.getHeight()) {
            throw new IllegalArgumentException("Crop area is out of image bounds");
        }
        
        BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);
        
        // Преобразуем в байты
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(croppedImage, "png", baos);
        
        return baos.toByteArray();
    }

    /**
     * Изменяет размер изображения с сохранением пропорций
     * 
     * @param imageBytes байты изображения
     * @param maxWidth максимальная ширина
     * @param maxHeight максимальная высота
     * @return измененное изображение в виде байтов
     */
    public byte[] resizeImage(byte[] imageBytes, int maxWidth, int maxHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        // Вычисляем новые размеры с сохранением пропорций
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);
        
        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);
        
        // Масштабирование изображения
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        
        // Преобразуем в байты
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", baos);
        
        return baos.toByteArray();
    }

    /**
     * Создает круглое изображение (как аватар)
     * 
     * @param imageBytes байты изображения
     * @return круглое изображение в виде байтов
     */
    public byte[] createCircularImage(byte[] imageBytes) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        
        // Определяем размер (берем минимум из ширины и высоты)
        int size = Math.min(originalImage.getWidth(), originalImage.getHeight());
        
        // Создаем новое изображение для круга
        BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = circularImage.createGraphics();
        
        // Включаем антиалиасинг для сглаживания
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Рисуем круг
        g.fillOval(0, 0, size, size);
        g.setComposite(AlphaComposite.SrcIn);
        
        // Рисуем исходное изображение
        int x = (size - originalImage.getWidth()) / 2;
        int y = (size - originalImage.getHeight()) / 2;
        g.drawImage(originalImage, x, y, null);
        g.dispose();
        
        // Преобразуем в байты
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(circularImage, "png", baos);
        
        return baos.toByteArray();
    }

    /**
     * Обрабатывает MultipartFile в байты
     */
    public byte[] multipartFileToBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }

    /**
     * Сохраняет байты в файл
     */
    public void saveBytesToFile(byte[] bytes, String filepath) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(filepath)) {
                bais.transferTo(fos);
            }
        }
    }
}

