-- Изменение структуры таблицы face_to_face
-- Этот скрипт делает start_time nullable и event_id NOT NULL

-- Удаляем ограничение NOT NULL с колонки start_time
ALTER TABLE face_to_face ALTER COLUMN start_time DROP NOT NULL;

-- Добавляем ограничение NOT NULL к колонке event_id
ALTER TABLE face_to_face ALTER COLUMN event_id SET NOT NULL;

-- Проверяем структуру таблицы
SELECT column_name, is_nullable, data_type 
FROM information_schema.columns 
WHERE table_name = 'face_to_face' 
ORDER BY ordinal_position;

