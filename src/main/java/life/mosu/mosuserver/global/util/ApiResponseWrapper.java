package life.mosu.mosuserver.global.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponseWrapper<T> {
    private final int status;
    private final String message;
    private final T data;

    private ApiResponseWrapper(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseWrapper<T> success(HttpStatus status, String message, T data) {
        return new ApiResponseWrapper<>(status, message, data);
    }

    public static <T> ApiResponseWrapper<T> success(HttpStatus status, T data) {
        return new ApiResponseWrapper<>(status, null, data);
    }

    public static ApiResponseWrapper<Void> success(HttpStatus status, String message) {
        return new ApiResponseWrapper<>(status, message, null);
    }

}