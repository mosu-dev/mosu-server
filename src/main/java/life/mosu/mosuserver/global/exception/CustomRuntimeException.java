package life.mosu.mosuserver.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class CustomRuntimeException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public CustomRuntimeException(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();

        log.error("CustomRuntimeException 발생: status={}, message={}", status, message, this);
    }

}
