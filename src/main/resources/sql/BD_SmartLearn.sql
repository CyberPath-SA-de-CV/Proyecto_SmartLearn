-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema SmartLearn
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
-- Table `smartlearn`.`TBL_ROL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_ROL` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_ROL` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` ENUM('ADMIN', 'ESTUDIANTE') NOT NULL,
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `nombre` ON `smartlearn`.`TBL_ROL` (`nombre` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_USUARIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_USUARIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_USUARIO` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `correo` VARCHAR(255) NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  `modo_audio` TINYINT(1) NULL DEFAULT NULL,
  `huella_digital` BLOB NULL DEFAULT NULL,
  `discapacidad` VARCHAR(255) NULL DEFAULT NULL,
  `id_rol` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  CONSTRAINT `usuario_ibfk_1`
    FOREIGN KEY (`id_rol`)
    REFERENCES `smartlearn`.`TBL_ROL` (`id_rol`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_rol` ON `smartlearn`.`TBL_USUARIO` (`id_rol` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_CONTENIDO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_CONTENIDO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_CONTENIDO` (
  `id_contenido` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('TEORICO', 'PRACTICO') NOT NULL,
  `creado_por` INT NULL DEFAULT NULL,
  `fecha_creacion` DATE NULL DEFAULT NULL,
  `ultima_edicion` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_contenido`),
  CONSTRAINT `contenido_ibfk_2`
    FOREIGN KEY (`creado_por`)
    REFERENCES `smartlearn`.`TBL_USUARIO` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `creado_por` ON `smartlearn`.`TBL_CONTENIDO` (`creado_por` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_TIPO_EJERCICIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_TIPO_EJERCICIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_TIPO_EJERCICIO` (
  `id_tipo` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `nombre` ON `smartlearn`.`TBL_TIPO_EJERCICIO` (`nombre` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_EJERCICIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_EJERCICIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_EJERCICIO` (
  `id_ejercicio` INT NOT NULL AUTO_INCREMENT,
  `id_contenido` INT NULL DEFAULT NULL,
  `id_tipo` INT NULL DEFAULT NULL,
  `descripcion` TEXT NOT NULL,
  `creado_por` INT NULL DEFAULT NULL,
  `fecha_creacion` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_ejercicio`),
  CONSTRAINT `ejercicio_ibfk_1`
    FOREIGN KEY (`id_contenido`)
    REFERENCES `smartlearn`.`TBL_CONTENIDO` (`id_contenido`),
  CONSTRAINT `ejercicio_ibfk_2`
    FOREIGN KEY (`id_tipo`)
    REFERENCES `smartlearn`.`TBL_TIPO_EJERCICIO` (`id_tipo`),
  CONSTRAINT `ejercicio_ibfk_3`
    FOREIGN KEY (`creado_por`)
    REFERENCES `smartlearn`.`TBL_USUARIO` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_contenido` ON `smartlearn`.`TBL_EJERCICIO` (`id_contenido` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `id_tipo` ON `smartlearn`.`TBL_EJERCICIO` (`id_tipo` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `creado_por` ON `smartlearn`.`TBL_EJERCICIO` (`creado_por` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_INTENTO_EJERCICIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_INTENTO_EJERCICIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_INTENTO_EJERCICIO` (
  `id_intento` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NULL DEFAULT NULL,
  `id_ejercicio` INT NULL DEFAULT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_intento`),
  CONSTRAINT `intentoejercicio_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `smartlearn`.`TBL_USUARIO` (`id_usuario`),
  CONSTRAINT `intentoejercicio_ibfk_2`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `smartlearn`.`TBL_EJERCICIO` (`id_ejercicio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_usuario` ON `smartlearn`.`TBL_INTENTO_EJERCICIO` (`id_usuario` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `id_ejercicio` ON `smartlearn`.`TBL_INTENTO_EJERCICIO` (`id_ejercicio` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_CALIFICACION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_CALIFICACION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_CALIFICACION` (
  `id_calificacion` INT NOT NULL AUTO_INCREMENT,
  `id_intento` INT NULL DEFAULT NULL,
  `puntaje` DECIMAL(5,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id_calificacion`),
  CONSTRAINT `calificacion_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`TBL_INTENTO_EJERCICIO` (`id_intento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`TBL_CALIFICACION` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_CONTENIDO_PRACTICO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_CONTENIDO_PRACTICO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_CONTENIDO_PRACTICO` (
  `id_contenido` INT NOT NULL,
  `instrucciones` TEXT NOT NULL,
  PRIMARY KEY (`id_contenido`),
  CONSTRAINT `contenidopractico_ibfk_1`
    FOREIGN KEY (`id_contenido`)
    REFERENCES `smartlearn`.`TBL_CONTENIDO` (`id_contenido`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_CONTENIDO_TEORICO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_CONTENIDO_TEORICO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_CONTENIDO_TEORICO` (
  `id_contenido` INT NOT NULL,
  `texto` TEXT NOT NULL,
  `tiene_audio` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id_contenido`),
  CONSTRAINT `contenidoteorico_ibfk_1`
    FOREIGN KEY (`id_contenido`)
    REFERENCES `smartlearn`.`TBL_CONTENIDO` (`id_contenido`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_MATERIA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_MATERIA` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_MATERIA` (
  `id_materia` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_materia`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_PREGUNTA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_PREGUNTA` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_PREGUNTA` (
  `id_pregunta` INT NOT NULL AUTO_INCREMENT,
  `enunciado` TEXT NOT NULL,
  `id_ejercicio` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_pregunta`),
  CONSTRAINT `pregunta_ibfk_1`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `smartlearn`.`TBL_EJERCICIO` (`id_ejercicio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_ejercicio` ON `smartlearn`.`TBL_PREGUNTA` (`id_ejercicio` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_OPCION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_OPCION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_OPCION` (
  `id_opcion` INT NOT NULL AUTO_INCREMENT,
  `texto` VARCHAR(255) NOT NULL,
  `es_correcta` TINYINT(1) NULL DEFAULT NULL,
  `id_pregunta` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_opcion`),
  CONSTRAINT `opcion_ibfk_1`
    FOREIGN KEY (`id_pregunta`)
    REFERENCES `smartlearn`.`TBL_PREGUNTA` (`id_pregunta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_pregunta` ON `smartlearn`.`TBL_OPCION` (`id_pregunta` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_RESPUESTA_USUARIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_RESPUESTA_USUARIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_RESPUESTA_USUARIO` (
  `id_respuesta` INT NOT NULL AUTO_INCREMENT,
  `id_intento` INT NULL DEFAULT NULL,
  `id_pregunta` INT NULL DEFAULT NULL,
  `respuesta_texto` TEXT NOT NULL,
  `correcta` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id_respuesta`),
  CONSTRAINT `respuestausuario_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`TBL_INTENTO_EJERCICIO` (`id_intento`),
  CONSTRAINT `respuestausuario_ibfk_2`
    FOREIGN KEY (`id_pregunta`)
    REFERENCES `smartlearn`.`TBL_PREGUNTA` (`id_pregunta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`TBL_RESPUESTA_USUARIO` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `id_pregunta` ON `smartlearn`.`TBL_RESPUESTA_USUARIO` (`id_pregunta` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_RETROALIMENTACION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_RETROALIMENTACION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_RETROALIMENTACION` (
  `id_retro` INT NOT NULL AUTO_INCREMENT,
  `id_intento` INT NULL DEFAULT NULL,
  `mensaje` TEXT NOT NULL,
  PRIMARY KEY (`id_retro`),
  CONSTRAINT `retroalimentacion_ibfk_1`
    FOREIGN KEY (`id_intento`)
    REFERENCES `smartlearn`.`TBL_INTENTO_EJERCICIO` (`id_intento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_intento` ON `smartlearn`.`TBL_RETROALIMENTACION` (`id_intento` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_TEMA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_TEMA` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_TEMA` (
  `id_tema` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `id_materia` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_tema`),
  CONSTRAINT `tema_ibfk_1`
    FOREIGN KEY (`id_materia`)
    REFERENCES `smartlearn`.`TBL_MATERIA` (`id_materia`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `id_materia` ON `smartlearn`.`TBL_TEMA` (`id_materia` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `smartlearn`.`TBL_SUBTEMA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartlearn`.`TBL_SUBTEMA` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `smartlearn`.`TBL_SUBTEMA` (
  `id_subtema` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `contenido_id_contenido` INT NOT NULL,
  `tema_id_tema` INT NOT NULL,
  PRIMARY KEY (`id_subtema`),
  CONSTRAINT `fk_subtema_contenido1`
    FOREIGN KEY (`contenido_id_contenido`)
    REFERENCES `smartlearn`.`TBL_CONTENIDO` (`id_contenido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subtema_tema1`
    FOREIGN KEY (`tema_id_tema`)
    REFERENCES `smartlearn`.`TBL_TEMA` (`id_tema`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE INDEX `fk_subtema_contenido1_idx` ON `smartlearn`.`TBL_SUBTEMA` (`contenido_id_contenido` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_subtema_tema1_idx` ON `smartlearn`.`TBL_SUBTEMA` (`tema_id_tema` ASC) VISIBLE;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
