alter table user_week1_information
    add close_person1_evaluation varchar(225) null after not_preferred_time;

alter table user_week1_information
    add close_person2_evaluation varchar(225) null after close_person1_evaluation;

