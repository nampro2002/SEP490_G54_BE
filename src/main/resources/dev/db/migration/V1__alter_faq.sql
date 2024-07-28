alter table faq
    add answer_en varchar(225) not null after answer;

alter table faq
    add question_en varchar(225) not null;

