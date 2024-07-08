ALTER TABLE user_lesson
    ADD CONSTRAINT unique_constraint_name UNIQUE (appuser_id);

alter table medical_appointment
    add result text null;
ALTER TABLE user_lesson
DROP FOREIGN KEY FKaoad4p1ijn3vbv7yyn1k52uk8;
drop table lesson cascade;
alter table user_lesson
drop column lesson_id ;

alter table user_lesson
    add lesson int null;

