--


--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('3d720ad0-1c26-492b-8caa-1d887914e3a7', 'Max', 'MALE', 'DOG', true, '2022-01-01', '2022-01-01T10:30:00Z', '2022-01-01T10:30:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('460c2acf-cfb6-486e-908a-f92b9227b6bf', 'Luna', 'FEMALE', 'CAT', false, '2021-05-15', '2022-02-05T14:45:00Z', '2022-02-05T14:45:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('c75f833f-b0de-4203-9c13-2caac3b6ebfc', 'Charlie', 'MALE', 'DOG', false, '2020-12-10', '2022-03-15T09:00:00Z', '2022-03-15T09:00:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('cd1bb68f-4863-4b5e-a55e-9849a9efbc3a', 'Bella', 'FEMALE', 'CAT', true, '2022-02-20', '2022-04-20T16:20:00Z', '2022-04-20T16:20:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('1525a4e5-26c4-4f20-a72d-f1d647986a91', 'Rocky', 'MALE', 'DOG', false, '2021-11-05', '2022-06-10T11:10:00Z', '2022-06-10T11:10:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('b0acfe80-65f2-4258-bd71-06d4eb00253c', 'Lily', 'FEMALE', 'DOG', true, '2022-03-28', '2022-07-25T18:30:00Z', '2022-07-25T18:30:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('b18077c6-58fc-409d-a4c1-17a9197264ab', 'Simba', 'MALE', 'CAT', false, '2020-08-12', '2022-09-15T13:15:00Z', '2022-09-15T13:15:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('d552defe-57cb-4c99-9619-2c767ad769a6', 'Milo', 'MALE', 'DOG', true, '2021-04-30', '2022-10-20T20:00:00Z', '2022-10-20T20:00:00Z');
--
--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('3e03e8e5-04ff-41ed-8ee9-2c45ca987f79', 'Mia', 'FEMALE', 'CAT', false, '2021-09-18', '2022-11-30T15:45:00Z', '2022-11-30T15:45:00Z');
CREATE TYPE Gender ENUM ('MALE', 'FEMALE');
CREATE TYPE Specie ENUM ('DOG', 'CAT','OTHER');
INSERT INTO pet_table (name, gender, specie, is_adopted, birth_date, register_on, modified_on)
VALUES ('Maximus', 'MALE', 'OTHER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--INSERT INTO pet_table (id, name, gender, specie, is_adopted, birth_date, register_on, modified_on)
--VALUES ('3d720ad0-1c26-492b-8caa-1d887914e3a7', 'Max', 'MALE', 'DOG', true, '2022-01-01', '2022-01-01T10:30:00Z', '2022-01-01T10:30:00Z')
--,('460c2acf-cfb6-486e-908a-f92b9227b6bf', 'Luna', 'FEMALE', 'CAT', false, '2021-05-15', '2022-02-05T14:45:00Z', '2022-02-05T14:45:00Z'),
--('c75f833f-b0de-4203-9c13-2caac3b6ebfc', 'Charlie', 'MALE', 'DOG', false, '2020-12-10', '2022-03-15T09:00:00Z', '2022-03-15T09:00:00Z'),
--('cd1bb68f-4863-4b5e-a55e-9849a9efbc3a', 'Bella', 'FEMALE', 'CAT', true, '2022-02-20', '2022-04-20T16:20:00Z', '2022-04-20T16:20:00Z'),
--('1525a4e5-26c4-4f20-a72d-f1d647986a91', 'Rocky', 'MALE', 'DOG', false, '2021-11-05', '2022-06-10T11:10:00Z', '2022-06-10T11:10:00Z')
--,('b0acfe80-65f2-4258-bd71-06d4eb00253c', 'Lily', 'FEMALE', 'DOG', true, '2022-03-28', '2022-07-25T18:30:00Z', '2022-07-25T18:30:00Z'),
--('b18077c6-58fc-409d-a4c1-17a9197264ab', 'Simba', 'MALE', 'CAT', false, '2020-08-12', '2022-09-15T13:15:00Z', '2022-09-15T13:15:00Z'),
--('d552defe-57cb-4c99-9619-2c767ad769a6', 'Milo', 'MALE', 'DOG', true, '2021-04-30', '2022-10-20T20:00:00Z', '2022-10-20T20:00:00Z')
--,('3e03e8e5-04ff-41ed-8ee9-2c45ca987f79', 'Mia', 'FEMALE', 'CAT', false, '2021-09-18', '2022-11-30T15:45:00Z', '2022-11-30T15:45:00Z')
--,('c7bafa2b-d204-45e9-bb01-0de8b1da45bc', 'Maximus', 'MALE', 'OTHER', true, '2022-06-01', '2023-01-05T09:20:00Z', '2023-01-05T09:20:00Z');

