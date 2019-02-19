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
  salary           BIGINT,
  department_id BIGINT
    CONSTRAINT employee_department_id_fkey
    REFERENCES department,
  email              TEXT,
  birth_date         TEXT
);


