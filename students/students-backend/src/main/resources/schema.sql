CREATE TABLE IF NOT EXISTS study_groups (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL UNIQUE,
    admission_year INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS teachers (
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email     VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS students (
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    birth_date DATE,
    group_id   BIGINT NOT NULL REFERENCES study_groups (id)
);

CREATE TABLE IF NOT EXISTS subjects (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(150) NOT NULL UNIQUE,
    hours      INT NOT NULL CHECK (hours > 0),
    teacher_id BIGINT NOT NULL REFERENCES teachers (id)
);

CREATE TABLE IF NOT EXISTS grades (
    id         BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES students (id) ON DELETE CASCADE,
    subject_id BIGINT NOT NULL REFERENCES subjects (id) ON DELETE CASCADE,
    score      INT    NOT NULL CHECK (score BETWEEN 1 AND 5),
    grade_date DATE   NOT NULL DEFAULT CURRENT_DATE
);

CREATE INDEX IF NOT EXISTS idx_students_group  ON students (group_id);
CREATE INDEX IF NOT EXISTS idx_grades_student  ON grades (student_id);
CREATE INDEX IF NOT EXISTS idx_grades_subject  ON grades (subject_id);
