# Учет студентов и оценок (Spring Boot + PostgreSQL)

Курсовой проект: backend REST API на Java 17 / Spring Boot 3 / PostgreSQL.

## Возможности
- 5 связанных таблиц, нормализация до 3NF
- Полный CRUD для всех сущностей (студенты, группы, преподаватели, предметы, оценки)
- Поиск студентов по имени/фамилии
- Отчеты (JOIN + агрегаты: AVG, MIN, MAX, COUNT)
- Валидация ввода (Bean Validation) и единый формат ошибок
- Стартовые данные загружаются автоматически при запуске
- Dockerfile + docker-compose

## Запуск через Docker (рекомендуется, подходит для сервера)
```bash
docker compose up --build -d
```
Приложение: http://localhost:8018/api/students

Остановить: `docker compose down` (с удалением данных БД: `docker compose down -v`).

## Запуск локально без Docker
1. Нужны Java 17, Maven и PostgreSQL.
2. Создать БД: `CREATE DATABASE studentsdb;`
3. Параметры подключения - переменные `DB_URL`, `DB_USER`, `DB_PASSWORD`
   (по умолчанию `jdbc:postgresql://localhost:5432/studentsdb`, `postgres` / `postgres`).
4. `mvn spring-boot:run`

Таблицы и стартовые данные создаются сами (`schema.sql`, `data.sql`).

## Эндпоинты

| Метод | URL | Описание |
|---|---|---|
| GET | `/api/students` | список студентов |
| GET | `/api/students?search=иван` | **поиск** по имени/фамилии |
| GET | `/api/students/{id}` | студент по id |
| GET | `/api/students/{id}/grades` | оценки студента |
| POST / PUT / DELETE | `/api/students[/{id}]` | создать / изменить / удалить |
| GET, POST, PUT, DELETE | `/api/groups` | CRUD групп |
| GET, POST, PUT, DELETE | `/api/teachers` | CRUD преподавателей |
| GET, POST, PUT, DELETE | `/api/subjects` | CRUD предметов |
| GET, POST, PUT, DELETE | `/api/grades` | CRUD оценок (фильтры `?studentId=&subjectId=`) |
| GET | `/api/reports/students-average` | **отчет**: средний балл студентов (рейтинг) |
| GET | `/api/reports/subjects-stats` | **отчет**: средний/мин/макс балл по предметам |
| GET | `/api/reports/groups-stats` | **отчет**: статистика по группам |

### Примеры тела запросов
```json
// POST /api/students
{ "firstName": "Игорь", "lastName": "Орлов", "email": "orlov@student.ru",
  "birthDate": "2005-02-10", "groupId": 1 }

// POST /api/grades
{ "studentId": 1, "subjectId": 2, "score": 5, "gradeDate": "2025-12-20" }

// POST /api/subjects
{ "name": "Физика", "hours": 72, "teacherId": 2 }

// POST /api/groups
{ "name": "ИС-23", "admissionYear": 2024 }

// POST /api/teachers
{ "fullName": "Орлова Ольга Петровна", "email": "orlova@univ.ru" }
```

Пример ошибки валидации (HTTP 400):
```json
{ "status": 400, "error": "Ошибка валидации",
  "details": { "score": "Оценка от 1 до 5", "email": "Некорректный email" } }
```

## ER-диаграмма
```mermaid
erDiagram
    STUDY_GROUPS ||--o{ STUDENTS : "содержит"
    TEACHERS     ||--o{ SUBJECTS : "ведет"
    STUDENTS     ||--o{ GRADES   : "получает"
    SUBJECTS     ||--o{ GRADES   : "по предмету"

    STUDY_GROUPS {
        bigint id PK
        varchar name UK
        int admission_year
    }
    TEACHERS {
        bigint id PK
        varchar full_name
        varchar email UK
    }
    STUDENTS {
        bigint id PK
        varchar first_name
        varchar last_name
        varchar email UK
        date birth_date
        bigint group_id FK
    }
    SUBJECTS {
        bigint id PK
        varchar name UK
        int hours
        bigint teacher_id FK
    }
    GRADES {
        bigint id PK
        bigint student_id FK
        bigint subject_id FK
        int score "1..5"
        date grade_date
    }
```

## Диаграмма классов
```mermaid
classDiagram
    class StudyGroup { Long id; String name; Integer admissionYear }
    class Teacher    { Long id; String fullName; String email }
    class Student    { Long id; String firstName; String lastName; String email; LocalDate birthDate }
    class Subject    { Long id; String name; Integer hours }
    class Grade      { Long id; Integer score; LocalDate gradeDate }

    Student "many" --> "1" StudyGroup : group
    Subject "many" --> "1" Teacher : teacher
    Grade   "many" --> "1" Student : student
    Grade   "many" --> "1" Subject : subject

    class StudentController
    class GradeController
    class SubjectController
    class ReportController
    class StudentService
    class GradeService
    class SubjectService
    class GradeRepository
    class StudentRepository

    StudentController --> StudentService
    GradeController   --> GradeService
    SubjectController --> SubjectService
    StudentService    --> StudentRepository
    GradeService      --> GradeRepository
    GradeService      --> StudentService
    GradeService      --> SubjectService
    ReportController  --> GradeRepository
```
Диаграммы в формате Mermaid отображаются на GitHub/GitLab. Чтобы получить картинку для
отчета: вставить код на https://mermaid.live и нажать Download PNG/SVG.

## Структура проекта
```
src/main/java/com/example/students
 ├── model/        JPA-сущности (таблицы)
 ├── repository/   доступ к БД (Spring Data JPA + JPQL-отчеты)
 ├── service/      бизнес-логика
 ├── controller/   REST-контроллеры
 ├── dto/          запросы (с валидацией) и DTO отчетов
 └── exception/    обработка ошибок
src/main/resources: application.properties, schema.sql, data.sql
```
