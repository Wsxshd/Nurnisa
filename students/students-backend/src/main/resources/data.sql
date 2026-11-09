INSERT INTO study_groups (id, name, admission_year) VALUES
 (1, 'ИС-21', 2023),
 (2, 'ИС-22', 2023),
 (3, 'ПМ-21', 2022)
ON CONFLICT (id) DO NOTHING;

INSERT INTO teachers (id, full_name, email) VALUES
 (1, 'Петров Алексей Иванович',   'petrov@univ.ru'),
 (2, 'Сидорова Мария Сергеевна',  'sidorova@univ.ru'),
 (3, 'Козлов Дмитрий Андреевич',  'kozlov@univ.ru')
ON CONFLICT (id) DO NOTHING;

INSERT INTO subjects (id, name, hours, teacher_id) VALUES
 (1, 'Базы данных',          72, 1),
 (2, 'Программирование Java', 108, 1),
 (3, 'Математика',            90, 2),
 (4, 'Английский язык',       54, 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO students (id, first_name, last_name, email, birth_date, group_id) VALUES
 (1, 'Иван',     'Иванов',    'ivanov@student.ru',    '2005-03-14', 1),
 (2, 'Анна',     'Смирнова',  'smirnova@student.ru',  '2005-07-21', 1),
 (3, 'Олег',     'Кузнецов',  'kuznetsov@student.ru', '2004-11-02', 1),
 (4, 'Мария',    'Попова',    'popova@student.ru',    '2005-01-30', 2),
 (5, 'Дмитрий',  'Волков',    'volkov@student.ru',    '2005-05-18', 2),
 (6, 'Екатерина','Соколова',  'sokolova@student.ru',  '2004-09-09', 2),
 (7, 'Павел',    'Морозов',   'morozov@student.ru',   '2003-12-25', 3),
 (8, 'Алиса',    'Новикова',  'novikova@student.ru',  '2004-04-12', 3),
 (9, 'Сергей',   'Лебедев',   'lebedev@student.ru',   '2004-08-03', 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO grades (id, student_id, subject_id, score, grade_date) VALUES
 (1,  1, 1, 5, '2025-12-10'),
 (2,  1, 2, 5, '2025-12-12'),
 (3,  1, 3, 4, '2025-12-15'),
 (4,  1, 4, 5, '2025-12-18'),
 (5,  2, 1, 4, '2025-12-10'),
 (6,  2, 2, 4, '2025-12-12'),
 (7,  2, 3, 3, '2025-12-15'),
 (8,  3, 1, 3, '2025-12-10'),
 (9,  3, 2, 3, '2025-12-12'),
 (10, 3, 3, 2, '2025-12-15'),
 (11, 3, 4, 4, '2025-12-18'),
 (12, 4, 1, 5, '2025-12-10'),
 (13, 4, 2, 5, '2025-12-12'),
 (14, 4, 3, 5, '2025-12-15'),
 (15, 5, 1, 4, '2025-12-10'),
 (16, 5, 2, 3, '2025-12-12'),
 (17, 5, 4, 4, '2025-12-18'),
 (18, 6, 1, 4, '2025-12-10'),
 (19, 6, 3, 5, '2025-12-15'),
 (20, 6, 4, 5, '2025-12-18'),
 (21, 7, 1, 3, '2025-12-10'),
 (22, 7, 2, 4, '2025-12-12'),
 (23, 7, 3, 3, '2025-12-15'),
 (24, 8, 1, 5, '2025-12-10'),
 (25, 8, 2, 4, '2025-12-12'),
 (26, 8, 4, 5, '2025-12-18'),
 (27, 9, 2, 3, '2025-12-12'),
 (28, 9, 3, 4, '2025-12-15'),
 (29, 9, 4, 3, '2025-12-18')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('study_groups', 'id'), (SELECT MAX(id) FROM study_groups));
SELECT setval(pg_get_serial_sequence('teachers', 'id'),     (SELECT MAX(id) FROM teachers));
SELECT setval(pg_get_serial_sequence('subjects', 'id'),     (SELECT MAX(id) FROM subjects));
SELECT setval(pg_get_serial_sequence('students', 'id'),     (SELECT MAX(id) FROM students));
SELECT setval(pg_get_serial_sequence('grades', 'id'),       (SELECT MAX(id) FROM grades));
