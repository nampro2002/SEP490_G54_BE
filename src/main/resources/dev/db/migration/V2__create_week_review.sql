

CREATE TABLE `smarthealthc`.`week_review` (
                                              `id` INT NOT NULL AUTO_INCREMENT,
                                              `appuser_id` INT NOT NULL,
                                              `week_start` DATETIME(6) NOT NULL,
                                              `hba1c_safe_record` INT NOT NULL,
                                              `hba1c_total_record` INT NOT NULL,
                                              `cholesterol_total_record` INT NOT NULL,
                                              `cholesterol_safe_record` INT NOT NULL,
                                              `blood_sugar_total_record` INT NOT NULL,
                                              `blood_sugar_safe_record` INT NOT NULL,
                                              `total_blood_pressure_record` INT NOT NULL,
                                              `safe_blood_pressure_record` INT NOT NULL,
                                              `average_weight_record_per_week` INT NOT NULL,
                                              `average_mental_record_per_week` INT NOT NULL,
                                              `heavy_activity` INT NOT NULL,
                                              `medium_activity` INT NOT NULL,
                                              `light_activity` INT NOT NULL,
                                              `average_diet_record_per_week` INT NOT NULL,
                                              `medicine_record_total` INT NOT NULL,
                                              `medicine_record_done` INT NOT NULL,
                                              `average_step_record_per_week` INT NOT NULL,
                                              PRIMARY KEY (`id`),
                                              INDEX `appuser_id_idx` (`appuser_id` ASC) VISIBLE,
                                              CONSTRAINT `appuser_id`
                                                  FOREIGN KEY (`appuser_id`)
                                                      REFERENCES `smarthealthc`.`app_user` (`id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION);

