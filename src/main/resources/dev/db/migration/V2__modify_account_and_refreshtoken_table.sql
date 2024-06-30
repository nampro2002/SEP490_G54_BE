alter table account
    modify type enum ('ADMIN', 'DOCTOR', 'MEDICAL_SPECIALIST', 'USER') not null;
alter table refresh_token
    add device_token varchar(255) null;