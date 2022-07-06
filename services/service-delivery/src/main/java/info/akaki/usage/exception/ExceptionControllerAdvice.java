package info.akaki.usage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ServiceDeliveryException.class)
    public ResponseEntity<ErrorInfo> serviceDeliveryExceptionHandler(ServiceDeliveryException exception) {
        final HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorInfo(exception.getMessage(), errorCode.value(), LocalDateTime.now()),
                errorCode
        );
    }
}
