package info.akaki.usage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorInfo {
    private final String errorMessage;
    private final Integer errorCode;
    private LocalDateTime errorTimestamp;
}
