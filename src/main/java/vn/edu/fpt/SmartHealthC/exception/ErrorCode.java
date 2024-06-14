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

    JWT_INVALID ( ",Invalid jwt token", HttpStatus.BAD_REQUEST),
    CREDENTIAL_EXPIRED ( "Your login session has expired", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("Email existed", HttpStatus.BAD_REQUEST),
    SEND_EMAIL_FAIL("Send email fail", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED("Email not existed", HttpStatus.BAD_REQUEST),
    CREDENTIAL_INVALID("Wrong email or password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission", HttpStatus.FORBIDDEN),
    APP_USER_NOT_FOUND("AppUser not found", HttpStatus.NOT_FOUND),
    USER_WEEK_START_NOT_EXIST("User not have record about week start", HttpStatus.NOT_FOUND),
    WEB_USER_NOT_FOUND("WebUser not found", HttpStatus.NOT_FOUND),
    BPS_NOT_FOUND("bps not found", HttpStatus.NOT_FOUND),
    NOT_FOUND("Not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),
    BLOOD_PRESSURE_NOT_FOUND("Blood pressure not found", HttpStatus.NOT_FOUND),
    DIET_RECORD_NOT_FOUND("Diet record not found", HttpStatus.NOT_FOUND),
    ACTIVITY_RECORD_NOT_FOUND("Activity record not found", HttpStatus.NOT_FOUND),
    FORM_QUESTION_NOT_FOUND("Form question not found", HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND("Lesson not found", HttpStatus.NOT_FOUND),
    USER_LESSON_NOT_FOUND("User lesson not found", HttpStatus.NOT_FOUND),
    MEDICINE_NOT_FOUND("Medicine not found", HttpStatus.NOT_FOUND),
    SAT_SF_C_NOT_FOUND("SAT_SF_C not found", HttpStatus.NOT_FOUND),
    SAT_SF_I_NOT_FOUND("SAT_SF_I not found", HttpStatus.NOT_FOUND),
    SAT_SF_P_NOT_FOUND("SAT_SF_P not found", HttpStatus.NOT_FOUND),
    SF_NOT_FOUND("SF not found", HttpStatus.NOT_FOUND),
    STEP_RECORD_NOT_FOUND("Step record not found", HttpStatus.NOT_FOUND),
    QUESTION_NOT_FOUND("Question not found", HttpStatus.NOT_FOUND),
    CARDINAL_NOT_FOUND("Numeral not found", HttpStatus.NOT_FOUND),
    MONTHLY_QUESTION_NOTFOUND("Monthly Question not found", HttpStatus.NOT_FOUND),
    RULE_FOR_PLAN_NOTFOUND("Rule For Plan not found", HttpStatus.NOT_FOUND),
    MENTAL_RULE_NOT_FOUND("Mental Rule not found", HttpStatus.NOT_FOUND),
    MENTAL_NOT_FOUND("Mental not found", HttpStatus.NOT_FOUND),
    MEDICINE_TYPE_PLAN_NOT_FOUND("Medicine Type Plan not found", HttpStatus.NOT_FOUND),
    MEDICINE_TYPE_NOT_FOUND("Medicine type not found", HttpStatus.NOT_FOUND),
    MEDICAL_APPOINTMENT_NOT_FOUND("Medical appointment not found", HttpStatus.NOT_FOUND),
    MEDICAL_HISTORY_NOT_FOUND("Medical history not found", HttpStatus.NOT_FOUND),
    USER_MEDICAL_HISTORY_NOT_FOUND("User medical history not found", HttpStatus.NOT_FOUND),
    WEIGHT_RECORD_NOT_FOUND("Weight record not found", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND("Account not found", HttpStatus.NOT_FOUND),
    NULL_ANSWER("Answer is null", HttpStatus.BAD_REQUEST),
    USER_CREATED("User created successfully", HttpStatus.CREATED),
    STAFF_CREATED("Staff created successfully", HttpStatus.CREATED),
    WRONG_OLD_PASSWORD("Wrong old password", HttpStatus.BAD_REQUEST),
    ACCOUNT_DELETED("Account has been deleted", HttpStatus.OK),
    ACCOUNT_ACTIVATED("Account has been activated", HttpStatus.OK),
    ;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String message;
    private final HttpStatusCode statusCode;
}