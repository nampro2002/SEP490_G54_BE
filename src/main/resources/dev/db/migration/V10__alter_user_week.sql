ALTER TABLE `smarthealthc`.`user_week1_information`
    CHANGE COLUMN `prefrerred_time` `prefrerred_time` VARCHAR(225) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL ,
    CHANGE COLUMN `not_preferred_time` `not_preferred_time` VARCHAR(225) CHARACTER SET 'utf8mb4' NULL DEFAULT NULL ;
