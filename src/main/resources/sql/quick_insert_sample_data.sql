-- Быстрая вставка тестовых данных (5 пользователей + 10 автомобилей)

-- Пользователи (с добавленной колонкой name)
INSERT INTO users (name, first_name, last_name, email, phone, instagram, profile_photo_url, motto, official_photo_url, sponsors) VALUES
('Иван Смирнов', 'Иван', 'Смирнов', 'ivan.smirnov@example.com', '+7-911-111-1111', '@ivan_drift', 'https://example.com/photos/ivan_profile.jpg', 'Дрифт - это искусство движения', 'https://example.com/photos/ivan_official.jpg', 'Red Bull, Toyota Racing'),
('Елена Кузнецова', 'Елена', 'Кузнецова', 'elena.kuznetsova@example.com', '+7-922-222-2222', '@elena_drift_queen', 'https://example.com/photos/elena_profile.jpg', 'Скорость без границ', 'https://example.com/photos/elena_official.jpg', 'Monster Energy, Nissan Motorsports'),
('Андрей Попов', 'Андрей', 'Попов', 'andrey.popov@example.com', '+7-933-333-3333', '@andrey_drift_master', 'https://example.com/photos/andrey_profile.jpg', 'Каждый поворот - новая история', 'https://example.com/photos/andrey_official.jpg', 'Shell, BMW Performance'),
('Ольга Васильева', 'Ольга', 'Васильева', 'olga.vasilieva@example.com', '+7-944-444-4444', '@olga_drift_girl', 'https://example.com/photos/olga_profile.jpg', 'Дрифт - это страсть, которая движет мной', 'https://example.com/photos/olga_official.jpg', 'Castrol, Honda Racing Team'),
('Михаил Соколов', 'Михаил', 'Соколов', 'mikhail.sokolov@example.com', '+7-955-555-5555', '@mikhail_drift_king', 'https://example.com/photos/mikhail_profile.jpg', 'Побеждай или учись - третьего не дано', 'https://example.com/photos/mikhail_official.jpg', 'Mobil 1, Mercedes-AMG');

-- Автомобили
INSERT INTO cars (brand, model, horsepower, user_photo_url, moderator_photo_url, year, color, car_class, color1, color2, color3, user_id) VALUES
('Toyota', 'Supra A80', 330, 'https://example.com/photos/supra_user.jpg', 'https://example.com/photos/supra_moderator.jpg', 1998, 'Белый', 'Спорткар', 'Белый', 'Черный', 'Красный', 1),
('Nissan', 'Skyline GT-R R34', 280, 'https://example.com/photos/r34_user.jpg', 'https://example.com/photos/r34_moderator.jpg', 1999, 'Синий', 'Спорткар', 'Синий', 'Серебристый', 'Белый', 1),
('Nissan', 'Silvia S15', 280, 'https://example.com/photos/silvia_user.jpg', 'https://example.com/photos/silvia_moderator.jpg', 2000, 'Фиолетовый', 'Спорткар', 'Фиолетовый', 'Черный', 'Розовый', 2),
('Toyota', 'Chaser JZX100', 280, 'https://example.com/photos/chaser_user.jpg', 'https://example.com/photos/chaser_moderator.jpg', 1996, 'Черный', 'Спортседан', 'Черный', 'Белый', 'Серебристый', 2),
('BMW', 'E36 M3', 320, 'https://example.com/photos/e36_user.jpg', 'https://example.com/photos/e36_moderator.jpg', 1995, 'Желтый', 'Спорткар', 'Желтый', 'Черный', 'Белый', 3),
('BMW', 'E46 M3', 343, 'https://example.com/photos/e46_user.jpg', 'https://example.com/photos/e46_moderator.jpg', 2003, 'Красный', 'Спорткар', 'Красный', 'Черный', 'Серебристый', 3),
('Honda', 'S2000', 240, 'https://example.com/photos/s2000_user.jpg', 'https://example.com/photos/s2000_moderator.jpg', 2001, 'Красный', 'Родстер', 'Красный', 'Черный', 'Белый', 4),
('Mazda', 'RX-7 FD3S', 255, 'https://example.com/photos/rx7_user.jpg', 'https://example.com/photos/rx7_moderator.jpg', 1998, 'Белый', 'Спорткар', 'Белый', 'Красный', 'Черный', 4),
('Mercedes-Benz', 'C63 AMG', 450, 'https://example.com/photos/c63_user.jpg', 'https://example.com/photos/c63_moderator.jpg', 2012, 'Серебристый', 'Спортседан', 'Серебристый', 'Черный', 'Белый', 5),
('Toyota', 'AE86 Trueno', 130, 'https://example.com/photos/ae86_user.jpg', 'https://example.com/photos/ae86_moderator.jpg', 1986, 'Черно-белый', 'Классика', 'Черный', 'Белый', 'Красный', 5);