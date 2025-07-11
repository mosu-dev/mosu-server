package life.mosu.mosuserver.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class CustomRuntimeException extends RuntimeException {

    private final HttpStatus status;
    private final String message;
    private final String code;

    public CustomRuntimeException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();

        log.error("CustomRuntimeException 발생: status={}, message={}", status, message, this);
    }

    public CustomRuntimeException(ErrorCode errorCode, Object... cause) {
        this.status = errorCode.getStatus();
        this.message = String.format(errorCode.getMessage(), cause);
        this.code = errorCode.name();

        log.error("CustomRuntimeException 발생: status={}, message={}", status, message, this);
    }
}