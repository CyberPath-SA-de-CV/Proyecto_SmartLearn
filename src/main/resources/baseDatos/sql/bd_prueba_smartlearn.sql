-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema smartlearn
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `smartlearn` ;

-- -----------------------------------------------------
-- Schema smartlearn
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `smartlearn` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
SHOW WARNINGS;
USE `smartlearn` ;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_rol`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_rol` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_rol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` ENUM('ADMIN', 'ESTUDIANTE') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `nombre` ON `smartlearn`.`tbl_rol` (`nombre` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_usuario` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `correo` VARCHAR(255) NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  `modo_audio` TINYINT(1) NULL DEFAULT NULL,
  `huella_digital` BLOB NULL DEFAULT NULL,
  `discapacidad` VARCHAR(255) NULL DEFAULT NULL,
  `id_rol` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `usuario_ibfk_1`
    FOREIGN KEY (`id_rol`)
    REFERENCES `smartlearn`.`tbl_rol` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_rol` ON `smartlearn`.`tbl_usuario` (`id_rol` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_tipo_ejercicio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_tipo_ejercicio` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_tipo_ejercicio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `nombre` ON `smartlearn`.`tbl_tipo_ejercicio` (`nombre` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_materia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_materia` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_materia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_tema`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_tema` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_tema` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `id_materia` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `tema_ibfk_1`
    FOREIGN KEY (`id_materia`)
    REFERENCES `smartlearn`.`tbl_materia` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_materia` ON `smartlearn`.`tbl_tema` (`id_materia` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_contenido_teorico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_contenido_teorico` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_contenido_teorico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `texto` VARCHAR(8000) NOT NULL,
  `fecha_creacion` DATE NULL DEFAULT NULL,
  `ultima_edicion` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_subtema`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_subtema` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_subtema` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `id_tema` INT NOT NULL,
  `id_contenido` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_subtema_tema1`
    FOREIGN KEY (`id_tema`)
    REFERENCES `smartlearn`.`tbl_tema` (`id`),
  CONSTRAINT `fk_tbl_subtema_tbl_contenido_teorico1`
    FOREIGN KEY (`id_contenido`)
    REFERENCES `smartlearn`.`tbl_contenido_teorico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `fk_subtema_tema1_idx` ON `smartlearn`.`tbl_subtema` (`id_tema` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_tbl_subtema_tbl_contenido_teorico1_idx` ON `smartlearn`.`tbl_subtema` (`id_contenido` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_ejercicio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_ejercicio` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_ejercicio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_tipo` INT NOT NULL,
  `descripcion` TEXT NOT NULL,
  `creado_por` INT NULL DEFAULT NULL,
  `fecha_creacion` DATE NULL DEFAULT NULL,
  `instrucciones` VARCHAR(250) NULL DEFAULT NULL,
  `id_subtema` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ejercicio_ibfk_2`
    FOREIGN KEY (`id_tipo`)
    REFERENCES `smartlearn`.`tbl_tipo_ejercicio` (`id`),
  CONSTRAINT `ejercicio_ibfk_3`
    FOREIGN KEY (`creado_por`)
    REFERENCES `smartlearn`.`tbl_usuario` (`id`),
  CONSTRAINT `fk_tbl_ejercicio_tbl_subtema1`
    FOREIGN KEY (`id_subtema`)
    REFERENCES `smartlearn`.`tbl_subtema` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_tipo` ON `smartlearn`.`tbl_ejercicio` (`id_tipo` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `creado_por` ON `smartlearn`.`tbl_ejercicio` (`creado_por` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_tbl_ejercicio_tbl_subtema1_idx` ON `smartlearn`.`tbl_ejercicio` (`id_subtema` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_intento_ejercicio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_intento_ejercicio` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_intento_ejercicio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NULL DEFAULT NULL,
  `id_ejercicio` INT NULL DEFAULT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `intentoejercicio_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `smartlearn`.`tbl_usuario` (`id`),
  CONSTRAINT `intentoejercicio_ibfk_2`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `smartlearn`.`tbl_ejercicio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_usuario` ON `smartlearn`.`tbl_intento_ejercicio` (`id_usuario` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `id_ejercicio` ON `smartlearn`.`tbl_intento_ejercicio` (`id_ejercicio` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_calificacion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_calificacion` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_calificacion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_intento` INT NULL DEFAULT NULL,
  `puntaje` DECIMAL(5,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `calificacion_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`tbl_intento_ejercicio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`tbl_calificacion` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_pregunta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_pregunta` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_pregunta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `enunciado` TEXT NOT NULL,
  `id_ejercicio` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `pregunta_ibfk_1`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `smartlearn`.`tbl_ejercicio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_ejercicio` ON `smartlearn`.`tbl_pregunta` (`id_ejercicio` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_opcion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_opcion` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_opcion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `texto` VARCHAR(255) NOT NULL,
  `es_correcta` TINYINT(1) NULL DEFAULT NULL,
  `id_pregunta` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `opcion_ibfk_1`
    FOREIGN KEY (`id_pregunta`)
    REFERENCES `smartlearn`.`tbl_pregunta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_pregunta` ON `smartlearn`.`tbl_opcion` (`id_pregunta` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_respuesta_usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_respuesta_usuario` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_respuesta_usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_intento` INT NULL DEFAULT NULL,
  `id_pregunta` INT NULL DEFAULT NULL,
  `respuesta_texto` TEXT NOT NULL,
  `correcta` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `respuestausuario_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`tbl_intento_ejercicio` (`id`),
  CONSTRAINT `respuestausuario_ibfk_2`
    FOREIGN KEY (`id_pregunta`)
    REFERENCES `smartlearn`.`tbl_pregunta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`tbl_respuesta_usuario` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `id_pregunta` ON `smartlearn`.`tbl_respuesta_usuario` (`id_pregunta` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`tbl_retroalimentacion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`tbl_retroalimentacion` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`tbl_retroalimentacion` (
  `id` INT NOT NULL,
  `id_intento` INT NOT NULL AUTO_INCREMENT,
  `mensaje` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `retroalimentacion_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`tbl_intento_ejercicio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`tbl_retroalimentacion` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
