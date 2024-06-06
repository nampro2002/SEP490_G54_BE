package vn.edu.fpt.SmartHealthC.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
//    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
//    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
//    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    CREDENTIAL_INVALID("Wrong email or password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission", HttpStatus.FORBIDDEN),
    APP_USER_NOT_FOUND("AppUser not found", HttpStatus.NOT_FOUND),
    ACTIVITY_RECORD_NOT_FOUND("Activity record not found", HttpStatus.NOT_FOUND)
//    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
//    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
//    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
//    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String message;
    private final HttpStatusCode statusCode;
}