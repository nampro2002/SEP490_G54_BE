package vn.edu.fpt.SmartHealthC.exception;

import java.util.Map;
import java.util.Objects;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        if (Objects.isNull(exception.getMessage())) {
            apiResponse.setMessage(exception.toString());
        } else {
            apiResponse.setMessage(exception.getMessage());
        }

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    ResponseEntity<ApiResponse> handlingExpiredJwtException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.CREDENTIAL_EXPIRED.getStatusCode().value());
        apiResponse.setMessage(ErrorCode.CREDENTIAL_EXPIRED.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = JwtException.class)
    ResponseEntity<ApiResponse> handlingJwtException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.JWT_INVALID.getStatusCode().value());
        apiResponse.setMessage(ErrorCode.JWT_INVALID.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getStatusCode().value());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public ResponseEntity<ApiResponse> handleBindException(BindException e) {
        // Trả về message của lỗi đầu tiên
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getStatusCode().value())
                        .message(errorCode.getMessage())
                        .build());
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
//        String enumKey = exception.getFieldError().getDefaultMessage();
//
//        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//        Map<String, Object> attributes = null;
//        try {
//            errorCode = ErrorCode.valueOf(enumKey);
//
//            var constraintViolation =
//                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
//
//            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
//
//            log.info(attributes.toString());
//
//        } catch (IllegalArgumentException e) {
//
//        }
//
//        ApiResponse apiResponse = new ApiResponse();
//
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(
//                Objects.nonNull(attributes)
//                        ? mapAttribute(errorCode.getMessage(), attributes)
//                        : errorCode.getMessage());
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}