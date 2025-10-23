-- Исправление структуры таблицы events для поддержки NULL в schedule_id
-- Этот скрипт изменяет колонку schedule_id чтобы она могла быть NULL

-- Удаляем ограничение NOT NULL с колонки schedule_id
ALTER TABLE events ALTER COLUMN schedule_id DROP NOT NULL;

-- Проверяем структуру таблицы
SELECT column_name, is_nullable, data_type 
FROM information_schema.columns 
WHERE table_name = 'events' 
ORDER BY ordinal_position;
