CREATE DATABASE IF NOT EXISTS BierDB;
USE BierDB;

DROP TABLE IF EXISTS `drink`;
DROP TABLE IF EXISTS `type`;
DROP TABLE IF EXISTS `contribution`;
DROP TABLE IF EXISTS `user`;
DROP VIEW IF EXISTS `available_drinks`;

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

CREATE TABLE `user`(
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255),
    `username` VARCHAR(255)
);

CREATE TABLE `contribution`(
    `contribution_id` INT AUTO_INCREMENT PRIMARY KEY,
    `drink_id` INT,
    `user_id` INT,
    `date_created` DATE,
    `accepted` BOOLEAN DEFAULT false,

    FOREIGN KEY (drink_id) REFERENCES `drink`(drink_id),
    FOREIGN KEY (user_id) REFERENCES `user`(user_id)
);

CREATE VIEW `available_drinks` AS
    SELECT `drink`.* FROM `contribution`
    RIGHT JOIN `drink` ON `contribution`.drink_id = `drink`.drink_id
    WHERE accepted IS NULL
    OR accepted = true;
