package info.akaki.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(SubscriptionManagementException.class)
    public ResponseEntity<ErrorInfo> handleSubscriptionManagementException(SubscriptionManagementException exception) {
        final HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        final String errorMessage = exception.getMessage();
        return new ResponseEntity<>(
                new ErrorInfo(errorMessage, errorCode.value(), LocalDateTime.now()),
                errorCode
        );
    }
}
