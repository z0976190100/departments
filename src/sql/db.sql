CREATE TABLE department
(
  title     TEXT,
  id        SERIAL NOT NULL
    CONSTRAINT department_pkey
    PRIMARY KEY,
  UNIQUE (title)
);

CREATE TABLE employee
(
  id                 SERIAL NOT NULL
    CONSTRAINT employee_pkey
    PRIMARY KEY,
  name     TEXT,
  email              TEXT,
  birth_date         DATE,
  age           INT,
  department_id BIGINT
    CONSTRAINT employee_department_id_fkey
    REFERENCES department

);


