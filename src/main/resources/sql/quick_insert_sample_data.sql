-- Быстрая вставка тестовых данных (5 пользователей + 10 автомобилей + классы шин)

-- Классы шин
INSERT INTO tyre_classes (tyre_class) VALUES
                                          ('225'),
                                          ('235'),
                                          ('245'),
                                          ('255'),
                                          ('265'),
                                          ('275'),
                                          ('285'),
                                          ('295'),
                                          ('305'),
                                          ('315')
    ON CONFLICT (tyre_class) DO NOTHING;

-- Пользователи (с добавленной колонкой name)
INSERT INTO users (first_name, last_name, email, phone, instagram, profile_photo_url, motto, official_photo_url, sponsors) VALUES
                                                                                                                               ('Иван', 'Смирнов', 'ivan.smirnov@example.com', '+7-911-111-1111', '@ivan_drift', 'https://example.com/photos/ivan_profile.jpg', 'Дрифт - это искусство движения', 'https://example.com/photos/ivan_official.jpg', 'Red Bull, Toyota Racing'),
                                                                                                                               ('Елена', 'Кузнецова', 'elena.kuznetsova@example.com', '+7-922-222-2222', '@elena_drift_queen', 'https://example.com/photos/elena_profile.jpg', 'Скорость без границ', 'https://example.com/photos/elena_official.jpg', 'Monster Energy, Nissan Motorsports'),
                                                                                                                               ('Андрей', 'Попов', 'andrey.popov@example.com', '+7-933-333-3333', '@andrey_drift_master', 'https://example.com/photos/andrey_profile.jpg', 'Каждый поворот - новая история', 'https://example.com/photos/andrey_official.jpg', 'Shell, BMW Performance'),
                                                                                                                               ('Ольга', 'Васильева', 'olga.vasilieva@example.com', '+7-944-444-4444', '@olga_drift_girl', 'https://example.com/photos/olga_profile.jpg', 'Дрифт - это страсть, которая движет мной', 'https://example.com/photos/olga_official.jpg', 'Castrol, Honda Racing Team'),
                                                                                                                               ('Михаил', 'Соколов', 'mikhail.sokolov@example.com', '+7-955-555-5555', '@mikhail_drift_king', 'https://example.com/photos/mikhail_profile.jpg', 'Побеждай или учись - третьего не дано', 'https://example.com/photos/mikhail_official.jpg', 'Mobil 1, Mercedes-AMG');

-- Автомобили
INSERT INTO cars (brand, model, horsepower, user_photo_url, moderator_photo_url, year, color, color1, color2, color3, user_id) VALUES
                                                                                                                                   ('Toyota', 'Supra A80', 330, 'https://example.com/photos/supra_user.jpg', 'https://example.com/photos/supra_moderator.jpg', 1998, 'Белый', 'Белый', 'Черный', 'Красный', 1),
                                                                                                                                   ('Nissan', 'Skyline GT-R R34', 280, 'https://example.com/photos/r34_user.jpg', 'https://example.com/photos/r34_moderator.jpg', 1999, 'Синий', 'Синий', 'Серебристый', 'Белый', 1),
                                                                                                                                   ('Nissan', 'Silvia S15', 280, 'https://example.com/photos/silvia_user.jpg', 'https://example.com/photos/silvia_moderator.jpg', 2000, 'Фиолетовый', 'Фиолетовый', 'Черный', 'Розовый', 2),
                                                                                                                                   ('Toyota', 'Chaser JZX100', 280, 'https://example.com/photos/chaser_user.jpg', 'https://example.com/photos/chaser_moderator.jpg', 1996, 'Черный', 'Черный', 'Белый', 'Серебристый', 2),
                                                                                                                                   ('BMW', 'E36 M3', 320, 'https://example.com/photos/e36_user.jpg', 'https://example.com/photos/e36_moderator.jpg', 1995, 'Желтый', 'Желтый', 'Черный', 'Белый', 3),
                                                                                                                                   ('BMW', 'E46 M3', 343, 'https://example.com/photos/e46_user.jpg', 'https://example.com/photos/e46_moderator.jpg', 2003, 'Красный', 'Красный', 'Черный', 'Серебристый', 3),
                                                                                                                                   ('Honda', 'S2000', 240, 'https://example.com/photos/s2000_user.jpg', 'https://example.com/photos/s2000_moderator.jpg', 2001, 'Красный', 'Красный', 'Черный', 'Белый', 4),
                                                                                                                                   ('Mazda', 'RX-7 FD3S', 255, 'https://example.com/photos/rx7_user.jpg', 'https://example.com/photos/rx7_moderator.jpg', 1998, 'Белый', 'Белый', 'Красный', 'Черный', 4),
                                                                                                                                   ('Mercedes-Benz', 'C63 AMG', 450, 'https://example.com/photos/c63_user.jpg', 'https://example.com/photos/c63_moderator.jpg', 2012, 'Серебристый', 'Серебристый', 'Черный', 'Белый', 5),
                                                                                                                                   ('Toyota', 'AE86 Trueno', 130, 'https://example.com/photos/ae86_user.jpg', 'https://example.com/photos/ae86_moderator.jpg', 1986, 'Черно-белый', 'Черный', 'Белый', 'Красный', 5);

-- Связи между автомобилями и классами шин (ManyToMany)

INSERT INTO car_tyre_classes (car_id, tyre_class_id) VALUES
-- Toyota Supra A80 (ID: 1) - использует шины 255, 265, 275
(1, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
(1, (SELECT id FROM tyre_classes WHERE tyre_class = '265')),
(1, (SELECT id FROM tyre_classes WHERE tyre_class = '275')),
-- Nissan Skyline GT-R R34 (ID: 2) - использует шины 245, 255, 265
(2, (SELECT id FROM tyre_classes WHERE tyre_class = '245')),
(2, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
(2, (SELECT id FROM tyre_classes WHERE tyre_class = '265')),
-- Nissan Silvia S15 (ID: 3) - использует шины 235, 245, 255
(3, (SELECT id FROM tyre_classes WHERE tyre_class = '235')),
(3, (SELECT id FROM tyre_classes WHERE tyre_class = '245')),
(3, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
-- Toyota Chaser JZX100 (ID: 4) - использует шины 255, 265, 275
(4, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
(4, (SELECT id FROM tyre_classes WHERE tyre_class = '265')),
(4, (SELECT id FROM tyre_classes WHERE tyre_class = '275')),
-- BMW E36 M3 (ID: 5) - использует шины 245, 255, 265
(5, (SELECT id FROM tyre_classes WHERE tyre_class = '245')),
(5, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
(5, (SELECT id FROM tyre_classes WHERE tyre_class = '265')),
-- BMW E46 M3 (ID: 6) - использует шины 255, 265, 275, 285
(6, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
(6, (SELECT id FROM tyre_classes WHERE tyre_class = '265')),
(6, (SELECT id FROM tyre_classes WHERE tyre_class = '275')),
(6, (SELECT id FROM tyre_classes WHERE tyre_class = '285')),
-- Honda S2000 (ID: 7) - использует шины 225, 235, 245
(7, (SELECT id FROM tyre_classes WHERE tyre_class = '225')),
(7, (SELECT id FROM tyre_classes WHERE tyre_class = '235')),
(7, (SELECT id FROM tyre_classes WHERE tyre_class = '245')),
-- Mazda RX-7 FD3S (ID: 8) - использует шины 235, 245, 255
(8, (SELECT id FROM tyre_classes WHERE tyre_class = '235')),
(8, (SELECT id FROM tyre_classes WHERE tyre_class = '245')),
(8, (SELECT id FROM tyre_classes WHERE tyre_class = '255')),
-- Mercedes-Benz C63 AMG (ID: 9) - использует шины 275, 285, 295, 305
(9, (SELECT id FROM tyre_classes WHERE tyre_class = '275')),
(9, (SELECT id FROM tyre_classes WHERE tyre_class = '285')),
(9, (SELECT id FROM tyre_classes WHERE tyre_class = '295')),
(9, (SELECT id FROM tyre_classes WHERE tyre_class = '305')),
-- Toyota AE86 Trueno (ID: 10) - использует шины 225, 235
(10, (SELECT id FROM tyre_classes WHERE tyre_class = '225')),
(10, (SELECT id FROM tyre_classes WHERE tyre_class = '235'))
    ON CONFLICT (car_id, tyre_class_id) DO NOTHING;