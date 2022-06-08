INSERT INTO `type`(`name`)
    VALUES("beer");

INSERT INTO `drink`(`name`, `type_id`, `volume_percentage`, `size_liter`)
    VALUES
        ("Bitburger", 1, 4.9, 0.33),
        ("Veltins", 1, 4.9, 0.33);

INSERT INTO `user`(`email`, `username`)
    VALUES("oliver@bierdb.de", "Oliver-Schlüter");

INSERT INTO `contribution`(`drink_id`, `user_id`, `date_created`)
    VALUES(1, 1, CURDATE());
