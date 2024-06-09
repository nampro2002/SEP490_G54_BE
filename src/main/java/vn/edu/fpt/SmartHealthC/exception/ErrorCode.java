package vn.edu.fpt.SmartHealthC.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
//    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
//    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
//    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
//    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
//    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    JWT_INVALID ( "Invalid jwt token", HttpStatus.BAD_REQUEST),
    CREDENTIAL_EXPIRED ( "Your login session has expired", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    CREDENTIAL_INVALID("Wrong email or password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission", HttpStatus.FORBIDDEN),
    APP_USER_NOT_FOUND("AppUser not found", HttpStatus.NOT_FOUND),
    BPS_NOT_FOUND("bps not found", HttpStatus.NOT_FOUND),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),
    BLOOD_PRESSURE_NOT_FOUND("Blood pressure not found", HttpStatus.NOT_FOUND),
    DIET_RECORD_NOT_FOUND("Diet record not found", HttpStatus.NOT_FOUND),
    ACTIVITY_RECORD_NOT_FOUND("Activity record not found", HttpStatus.NOT_FOUND),
    FORM_QUESTION_NOT_FOUND("Form question not found", HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND("Lesson not found", HttpStatus.NOT_FOUND),
    MEDICAL_APPOINTMENT_NOT_FOUND("Medical appointment not found", HttpStatus.NOT_FOUND),
    MEDICAL_HISTORY_NOT_FOUND("Medical history not found", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String message;
    private final HttpStatusCode statusCode;
}