ALTER TABLE `smarthealthc`.`refresh_token`
    ADD CONSTRAINT `account_id`
        FOREIGN KEY (`account_id`)
            REFERENCES `smarthealthc`.`account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;
