-- Скрипт для заполнения базы данных тестовыми данными
-- Выполняется автоматически при запуске приложения

-- Очистка существующих данных (опционально)
-- DELETE FROM car_tyre_classes;
-- DELETE FROM user_roles;
-- DELETE FROM rounds;
-- DELETE FROM face_to_face;
-- DELETE FROM event_track_configs;
-- DELETE FROM events;
-- DELETE FROM schedule_elements;
-- DELETE FROM schedules;
-- DELETE FROM track_configs;
-- DELETE FROM tracks;
-- DELETE FROM tyre_classes;
-- DELETE FROM cars;
-- DELETE FROM users;

-- Вставка пользователей
INSERT INTO users (id, username, password, email, first_name, last_name, phone, instagram, profile_photo_url, motto, official_photo_url, sponsors) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUz0FfJ2O8/3Q8Q8Q8Q8Q8Q8Q', 'admin@drift.com', 'Админ', 'Админов', '+7-900-000-0001', '@admin_drift', 'https://example.com/photos/admin_profile.jpg', 'Администратор системы', 'https://example.com/photos/admin_official.jpg', 'Drift Masters'),
(2, 'alexey.petrov', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUz0FfJ2O8/3Q8Q8Q8Q8Q8Q8Q', 'alexey.petrov@example.com', 'Алексей', 'Петров', '+7-900-123-4567', '@alexey_drift', 'https://example.com/photos/alexey_profile.jpg', 'Дрифт - это не просто спорт, это образ жизни', 'https://example.com/photos/alexey_official.jpg', 'Red Bull, Toyota Racing'),
(3, 'maria.smirnova', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUz0FfJ2O8/3Q8Q8Q8Q8Q8Q8Q', 'maria.smirnova@example.com', 'Мария', 'Смирнова', '+7-900-234-5678', '@maria_drift', 'https://example.com/photos/maria_profile.jpg', 'Скорость и стиль - мои главные принципы', 'https://example.com/photos/maria_official.jpg', 'Nissan, Yokohama'),
(4, 'dmitry.kozlov', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUz0FfJ2O8/3Q8Q8Q8Q8Q8Q8Q', 'dmitry.kozlov@example.com', 'Дмитрий', 'Козлов', '+7-900-345-6789', '@dmitry_drift', 'https://example.com/photos/dmitry_profile.jpg', 'Каждый поворот - это новый вызов', 'https://example.com/photos/dmitry_official.jpg', 'BMW, Michelin'),
(5, 'anna.volkova', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVhUz0FfJ2O8/3Q8Q8Q8Q8Q8Q8Q', 'anna.volkova@example.com', 'Анна', 'Волкова', '+7-900-456-7890', '@anna_drift', 'https://example.com/photos/anna_profile.jpg', 'Дрифт - это искусство на асфальте', 'https://example.com/photos/anna_official.jpg', 'Subaru, Bridgestone');

-- Вставка ролей пользователей
INSERT INTO user_roles (user_id, role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER');

-- Вставка автомобилей
INSERT INTO cars (id, brand, model, horsepower, user_photo_url, moderator_photo_url, year, color, car_class, color1, color2, color3, user_id) VALUES
(1, 'Toyota', 'Supra', 340, 'https://example.com/cars/toyota_supra_user.jpg', 'https://example.com/cars/toyota_supra_moderator.jpg', 2020, 'Белый', 'Спорткар', 'Белый', 'Черный', 'Красный', 2),
(2, 'Nissan', 'Silvia S15', 280, 'https://example.com/cars/nissan_silvia_user.jpg', 'https://example.com/cars/nissan_silvia_moderator.jpg', 2019, 'Синий', 'Спорткар', 'Синий', 'Белый', 'Серебристый', 3),
(3, 'BMW', 'E46 M3', 333, 'https://example.com/cars/bmw_e46_user.jpg', 'https://example.com/cars/bmw_e46_moderator.jpg', 2021, 'Черный', 'Спорткар', 'Черный', 'Белый', 'Серый', 4),
(4, 'Subaru', 'BRZ', 200, 'https://example.com/cars/subaru_brz_user.jpg', 'https://example.com/cars/subaru_brz_moderator.jpg', 2022, 'Красный', 'Спорткар', 'Красный', 'Черный', 'Белый', 5),
(5, 'Mazda', 'RX-7', 280, 'https://example.com/cars/mazda_rx7_user.jpg', 'https://example.com/cars/mazda_rx7_moderator.jpg', 2018, 'Желтый', 'Спорткар', 'Желтый', 'Черный', 'Оранжевый', 2),
(6, 'Honda', 'S2000', 240, 'https://example.com/cars/honda_s2000_user.jpg', 'https://example.com/cars/honda_s2000_moderator.jpg', 2020, 'Серебристый', 'Спорткар', 'Серебристый', 'Черный', 'Белый', 3);

-- Вставка классов шин
INSERT INTO tyre_classes (id, tyre_class) VALUES
(1, 'Спортивные'),
(2, 'Полуслики'),
(3, 'Слики'),
(4, 'Дрифт'),
(5, 'Универсальные');

-- Связывание автомобилей с классами шин
INSERT INTO car_tyre_classes (car_id, tyre_class_id) VALUES
(1, 1), (1, 4),
(2, 2), (2, 4),
(3, 1), (3, 3),
(4, 2), (4, 5),
(5, 1), (5, 4),
(6, 2), (6, 3);

-- Вставка трасс
INSERT INTO tracks (id, state, address, thumbnail_url, instruction_url, notes) VALUES
(1, 'Калифорния', '123 Drift Street, Los Angeles, CA 90210', 'https://example.com/tracks/california_thumbnail.jpg', 'https://example.com/tracks/california_instructions.pdf', 'Отличная трасса для начинающих дрифтеров'),
(2, 'Токио', '456 Racing Avenue, Tokyo, Japan', 'https://example.com/tracks/tokyo_thumbnail.jpg', 'https://example.com/tracks/tokyo_instructions.pdf', 'Профессиональная трасса с множеством поворотов'),
(3, 'Москва', '789 Speed Boulevard, Moscow, Russia', 'https://example.com/tracks/moscow_thumbnail.jpg', 'https://example.com/tracks/moscow_instructions.pdf', 'Современная трасса с отличным покрытием'),
(4, 'Берлин', '321 Autobahn Street, Berlin, Germany', 'https://example.com/tracks/berlin_thumbnail.jpg', 'https://example.com/tracks/berlin_instructions.pdf', 'Техничная трасса для опытных водителей'),
(5, 'Сидней', '654 Harbour Drive, Sydney, Australia', 'https://example.com/tracks/sydney_thumbnail.jpg', 'https://example.com/tracks/sydney_instructions.pdf', 'Живописная трасса у моря');

-- Вставка конфигураций трасс
INSERT INTO track_configs (id, config, track_id) VALUES
(1, '{"weather": "sunny", "temperature": 25, "wind_speed": 5, "track_condition": "dry"}', 1),
(2, '{"weather": "cloudy", "temperature": 20, "wind_speed": 10, "track_condition": "damp"}', 1),
(3, '{"weather": "rainy", "temperature": 15, "wind_speed": 15, "track_condition": "wet"}', 2),
(4, '{"weather": "sunny", "temperature": 28, "wind_speed": 3, "track_condition": "dry"}', 3),
(5, '{"weather": "foggy", "temperature": 12, "wind_speed": 8, "track_condition": "slippery"}', 4);

-- Вставка расписаний
INSERT INTO schedules (id, name, description) VALUES
(1, 'Основное расписание', 'Главное расписание дрифт-соревнований'),
(2, 'Выходное расписание', 'Расписание для выходных дней'),
(3, 'Ночное расписание', 'Расписание для ночных заездов');

-- Вставка элементов расписания
INSERT INTO schedule_elements (id, start_time, end_time, description, schedule_id) VALUES
(1, '9:00AM', '10:00AM', 'Регистрация участников', 1),
(2, '10:00AM', '10:30AM', 'Технический осмотр автомобилей', 1),
(3, '10:30AM', '12:00PM', 'Практические заезды', 1),
(4, '12:00PM', '1:00PM', 'Обеденный перерыв', 1),
(5, '1:00PM', '5:00PM', 'Основные соревнования', 1);

-- Вставка событий
INSERT INTO events (id, date, schedule_id, driver_limit, spectator_limit, event_type, spectator_price, driver_price) VALUES
(1, '2024-03-15', 1, 20, 100, 'Дрифт-соревнование', 50.00, 150.00),
(2, '2024-03-16', 2, 15, 80, 'Ночные заезды', 75.00, 200.00),
(3, '2024-03-17', 3, 25, 120, 'Финальные соревнования', 100.00, 300.00);

-- Вставка конфигураций событий и трасс
INSERT INTO event_track_configs (id, event_id, track_id, track_config_id) VALUES
(1, 1, 1, 1),
(2, 2, 2, 3),
(3, 3, 3, 4);

-- Вставка FaceToFace
INSERT INTO face_to_face (id, start_time, user_photo1, user_photo2, user1_id, user2_id, auto_photo1, auto_photo2, auto_user1_id, auto_user2_id, event_id) VALUES
(1, '10:30AM', 'https://example.com/photos/user1_photo.jpg', 'https://example.com/photos/user2_photo.jpg', 2, 3, 'https://example.com/photos/auto1_photo.jpg', 'https://example.com/photos/auto2_photo.jpg', 1, 2, 1),
(2, '11:00AM', 'https://example.com/photos/user3_photo.jpg', 'https://example.com/photos/user4_photo.jpg', 4, 5, 'https://example.com/photos/auto3_photo.jpg', 'https://example.com/photos/auto4_photo.jpg', 3, 4, 1),
(3, '11:30AM', 'https://example.com/photos/user5_photo.jpg', 'https://example.com/photos/user6_photo.jpg', 2, 4, 'https://example.com/photos/auto5_photo.jpg', 'https://example.com/photos/auto6_photo.jpg', 5, 6, 2);

-- Вставка раундов
INSERT INTO rounds (id, round_number, user_id1, user_id2, user_winner_id, face_to_face_id) VALUES
(1, 1, 2, 3, 2, 1),
(2, 2, 2, 3, 3, 1),
(3, 3, 2, 3, 2, 1),
(4, 1, 4, 5, 4, 2),
(5, 2, 4, 5, 5, 2);

-- Обновление последовательностей (для PostgreSQL)
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('cars_id_seq', (SELECT MAX(id) FROM cars));
SELECT setval('tyre_classes_id_seq', (SELECT MAX(id) FROM tyre_classes));
SELECT setval('tracks_id_seq', (SELECT MAX(id) FROM tracks));
SELECT setval('track_configs_id_seq', (SELECT MAX(id) FROM track_configs));
SELECT setval('schedules_id_seq', (SELECT MAX(id) FROM schedules));
SELECT setval('schedule_elements_id_seq', (SELECT MAX(id) FROM schedule_elements));
SELECT setval('events_id_seq', (SELECT MAX(id) FROM events));
SELECT setval('event_track_configs_id_seq', (SELECT MAX(id) FROM event_track_configs));
SELECT setval('face_to_face_id_seq', (SELECT MAX(id) FROM face_to_face));
SELECT setval('rounds_id_seq', (SELECT MAX(id) FROM rounds));
