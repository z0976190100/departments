CREATE TABLE department
(
  title     TEXT,
  id        SERIAL NOT NULL
    CONSTRAINT department_pkey
    PRIMARY KEY
);

CREATE TABLE employee
(
  id                 SERIAL NOT NULL
    CONSTRAINT employee_pkey
    PRIMARY KEY,
  name     TEXT,
  email              TEXT,
  birth_date         TEXT,
  salary           BIGINT,
  department_id BIGINT
    CONSTRAINT employee_department_id_fkey
    REFERENCES department

);


