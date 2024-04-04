DROP DATABASE IF EXISTS `iztech_ring_db`;

-- Create the database
CREATE DATABASE IF NOT EXISTS `iztech_ring_db`;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema iztech_ring_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema iztech_ring_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `iztech_ring_db`;
USE `iztech_ring_db` ;

-- -----------------------------------------------------
-- Table `iztech_ring_db`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`route` (
  `bidirectional` BIT(1) NOT NULL,
  `total_duration_minutes` INT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`stop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`stop` (
  `latitude` DOUBLE NULL DEFAULT NULL,
  `longitude` DOUBLE NULL DEFAULT NULL,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`bus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`bus` (
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `capacity` INT NOT NULL,
  `current_route_id` BIGINT NULL DEFAULT NULL,
  `current_stop_id` BIGINT NULL DEFAULT NULL,
  `id` BIGINT NOT NULL,
  `last_gps_update_instant` DATETIME(6) NULL DEFAULT NULL,
  `direction` ENUM('OUTBOUND', 'INBOUND', 'UNKNOWN', 'ROUND_TRIP') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bus_route`
    FOREIGN KEY (`current_route_id`)
    REFERENCES `iztech_ring_db`.`route` (`id`),
  CONSTRAINT `fk_bus_stop`
    FOREIGN KEY (`current_stop_id`)
    REFERENCES `iztech_ring_db`.`stop` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`location` (
  `latitude` DOUBLE NULL DEFAULT NULL,
  `longitude` DOUBLE NULL DEFAULT NULL,
  `bus_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `timestamp` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_location_bus`
    FOREIGN KEY (`bus_id`)
    REFERENCES `iztech_ring_db`.`bus` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`route_assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`route_assignment` (
  `end_of_life_time` DATETIME(6) NULL DEFAULT NULL,
  `matched` BIT(1) NOT NULL,
  `bus_id` BIGINT NULL DEFAULT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `route_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_route_assignment_route`
    FOREIGN KEY (`route_id`)
    REFERENCES `iztech_ring_db`.`route` (`id`),
  CONSTRAINT `fk_route_assignment_bus`
    FOREIGN KEY (`bus_id`)
    REFERENCES `iztech_ring_db`.`bus` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`stop_on_route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`stop_on_route` (
  `arrival_time_from_start` INT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `route_id` BIGINT NULL DEFAULT NULL,
  `stop_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_stop_on_route_stop`
    FOREIGN KEY (`stop_id`)
    REFERENCES `iztech_ring_db`.`stop` (`id`),
  CONSTRAINT `fk_stop_on_route_route`
    FOREIGN KEY (`route_id`)
    REFERENCES `iztech_ring_db`.`route` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iztech_ring_db`.`timetable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iztech_ring_db`.`timetable` (
  `departure_time` TIME NULL DEFAULT NULL,
  `weekend_activity` BIT(1) NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `route_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_timetable_route`
    FOREIGN KEY (`route_id`)
    REFERENCES `iztech_ring_db`.`route` (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- Insert data into `route`
INSERT INTO `iztech_ring_db`.`route` (`bidirectional`, `total_duration_minutes`, `name`) VALUES
(1, 20, 'Kampus Içi Ring Guzergahi'),
(1, 20, 'Gulbahce');

-- Insert data into `stop`
INSERT INTO `stop` (`id`,`latitude`, `longitude`, `name`) VALUES
(1,38.31972, 26.64225, 'Rektorluk'),
(2,38.31607, 26.63999, 'Makina Muh.'),
(3,38.31629, 26.63819, 'Yabanci Diller.'),
(4,38.31798, 26.63859, 'Kimya Muh.'),
(5,38.32049, 26.63915, 'Kutuphane.'),
(6,38.32340, 26.63982, 'Spor Salonu.'),
(7,38.32388, 26.63756, 'Afad KYK Yurdu.'),
(8,38.32467, 26.63519, 'KYK Yurdu.'),
(9,38.32368, 26.63246, 'Matematik Bolumu.'),
(10,38.32428, 26.63096, 'Mimarlik Fak.');

-- Insert data into `bus`
INSERT INTO `bus` (`id`,`name`,`capacity`, `direction`) VALUES
(1,'Hyundai',30, 'UNKNOWN');

-- Insert data into `location`
-- INSERT INTO `location` (`latitude`, `longitude`, `bus_id`, `timestamp`) VALUES
-- (38.31978, 26.64197, 1, '2024-04-04 00:00:00');

-- Insert data into `route_assignment`
-- INSERT INTO `route_assignment` (`life_time_end`, `matched`, `bus_id`, `route_id`) VALUES
-- ('08:00:00', 1, 1, 1);

-- Insert data into `stop_on_route`
INSERT INTO `stop_on_route` (`arrival_time_from_start`, `route_id`, `stop_id`) VALUES
(0, 1, 1),
(1, 1, 2),
(2, 1, 3),
(3, 1, 4),
(4, 1, 5),
(5, 1, 6),
(6, 1, 7),
(7, 1, 8),
(8, 1, 9),
(9, 1, 10);


-- Insert data into `timetable`
INSERT INTO `timetable` (`departure_time`, `weekend_activity`, `route_id`) VALUES
('09:00:00', 0, 1),
('23:59:00', 0, 1);