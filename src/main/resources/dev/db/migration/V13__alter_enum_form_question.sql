ALTER TABLE `smarthealthc`.`form_question`
    DROP COLUMN `type`;
ALTER TABLE `smarthealthc`.`form_question`
    ADD COLUMN `type` ENUM('SAT_SF_C', 'SAT_SF_P', 'SAT_SF_I', 'SF_MEDICATION', 'SF_MENTAL', 'SF_DIET', 'SF_ACTIVITY', 'NEW_MONTH_MARK' ) NOT NULL;
