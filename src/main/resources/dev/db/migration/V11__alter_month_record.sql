ALTER TABLE `smarthealthc`.`monthly_record`
    CHANGE COLUMN `answer` `answer` INT NULL ,
    CHANGE COLUMN `month_start` `month_number` INT NOT NULL ,
    CHANGE COLUMN `monthly_record_type` `monthly_record_type` ENUM('SAT_SF_C', 'SAT_SF_P', 'SAT_SF_I', 'SF_Medication', 'SF_Diet', 'SF_Activity', 'SF_Mental', 'New_Month_Mark' ) NOT NULL ,
    CHANGE COLUMN `question` `question` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL ,
    CHANGE COLUMN `question_number` `question_number` INT NULL ;
