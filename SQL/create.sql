CREATE DATABASE IF NOT EXISTS BierDB;
USE BierDB;

DROP TABLE IF EXISTS drink;
DROP TABLE IF EXISTS `type`;

CREATE TABLE `type`(
    `type_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255)
);


CREATE TABLE `drink`(
    `drink_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255),
    `type_id` INT,
    `volume_percentage` DECIMAL(10,2),
    `size_liter` DECIMAL(10,2),

    FOREIGN KEY (type_id) REFERENCES `type`(type_id)
);
