CREATE TABLE pet_table (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255),
  gender VARCHAR(20),
  specie VARCHAR(20),
  is_adopted BOOLEAN,
  birth_date DATE,
  register_on TIMESTAMP,
  modified_on TIMESTAMP
);

INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
VALUES ('3d720ad0-1c26-492b-8caa-1d887914e3a7', 'Max', 'MALE', 'DOG', true, '2022-01-01', '2022-01-01 10:30:00', '2022-01-01 10:30:00');
