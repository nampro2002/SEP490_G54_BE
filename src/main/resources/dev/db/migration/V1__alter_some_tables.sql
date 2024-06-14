
ALTER TABLE `smarthealthc`.`monthly_record`
    CHANGE COLUMN `monthly_record_type` `monthly_record_type`
    ENUM(
    'SAT_SF_C',
    'SAT_SF_P',
    'SAT_SF_I',
    'SF_Medication',
    'SF_Diet',
    'SF_Activity',
    'SF_Mental'
    )
NULL DEFAULT NULL ;
