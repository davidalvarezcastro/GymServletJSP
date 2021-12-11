-- CREATE BATABASE
DROP DATABASE IF EXISTS `actividades`;
CREATE DATABASE actividades;
USE actividades;

-- CREATE TABLES
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
	id int AUTO_INCREMENT PRIMARY KEY,
	login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  perfilAcceso VARCHAR(45) NOT NULL
);

DROP TABLE IF EXISTS `actividades`;
CREATE TABLE `actividades` (
	id int AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL,
  descripcion TEXT NOT NULL,
	recomendaciones TEXT NOT NULL,
	docentes VARCHAR(250) NOT NULL,
	dias VARCHAR(60) NOT NULL,
	horario VARCHAR(20) NOT NULL,
	fechaInicio DATE NOT NULL,
	fechaFin DATE NOT NULL
);

DROP TABLE IF EXISTS `ficheros`;
CREATE TABLE `ficheros` (
	id int AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL,
  ruta VARCHAR(250) NOT NULL,
	idActividad INT NOT NULL,
	CONSTRAINT idActividad_FK FOREIGN KEY (idActividad) REFERENCES `actividades`.`actividades` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERTS
INSERT INTO `usuarios` (login, password, perfilAcceso) VALUES
('admin', 'admin', 'admin');
