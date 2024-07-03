CREATE TABLE notification_setting (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    account_id INT,
                                    type_notification ENUM('MEDICAL_APPOINTMENT_NOTIFICATION', 'QUESTION_NOTIFICATION', 'WEEKLY_REPORT_NOTIFICATION', 'MONTHLY_REPORT_NOTIFICATION', 'DAILY_NOTIFICATION', 'PLAN_NOTIFICATION') NOT NULL,
                                    status BOOLEAN NOT NULL,
                                    FOREIGN KEY (account_id) REFERENCES smarthealthc.account(id)
);