--ALTER TABLE patients DROP CONSTRAINT FK_RECIPES_PATIENID;
--ALTER TABLE doctors DROP CONSTRAINT FK_RECIPES_DOCTORID;
--ALTER TABLE priorities DROP CONSTRAINT FK_RECIPES_PRIORITYID;

--DROP TABLE patients;
--DROP TABLE doctors;
--DROP TABLE priorities;
--DROP TABLE recipes;

CREATE TABLE IF NOT EXISTS patients 
(
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,

  name VARCHAR(30) NOT NULL,

  surname  VARCHAR(30) NOT NULL,

  patronymic VARCHAR(30) NOT NULL,

  telephone VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS doctors 
(
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,

  name VARCHAR(30) NOT NULL,

  surname  VARCHAR(30) NOT NULL,

  patronymic VARCHAR(30) NOT NULL,

  specialization VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS priorities(
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,

  name VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS recipes 
(
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,

  discription VARCHAR(150) NOT NULL ,

  patientID BIGINT NOT NULL ,
 
  doctorID BIGINT NOT NULL ,

  calendar DATETIME NOT NULL ,
 
  validaty INT NOT NULL ,

  priorityID BIGINT NOT NULL ,
  
  CONSTRAINT fk_recipes_patienID FOREIGN KEY (patientID) REFERENCES patients (id) ON DELETE NO ACTION ON UPDATE CASCADE,
  
  CONSTRAINT fk_recipes_doctorID FOREIGN KEY (doctorID) REFERENCES doctors(id) ON DELETE NO ACTION ON UPDATE CASCADE,
  
  CONSTRAINT fk_recipes_priorityID FOREIGN KEY (priorityID) REFERENCES priorities (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);



