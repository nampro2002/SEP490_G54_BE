ALTER TABLE  `smarthealthc`.`form_question`
    ADD COLUMN `question_en` VARCHAR(225) not null after `question`;

ALTER TABLE  `smarthealthc`.`medical_history`
    ADD COLUMN `name_en` VARCHAR(225) not null AFTER `name`;

ALTER TABLE `smarthealthc`.`medicine_type`
    ADD COLUMN `title_en` VARCHAR(225)  not null after `title`;

ALTER TABLE  `smarthealthc`.`mental_rule`
    ADD COLUMN `title_en`  VARCHAR(225) not null after `title`;

ALTER TABLE  `smarthealthc`.`monthly_record`
    ADD COLUMN `question_en`  VARCHAR(225)  null after `question`;

