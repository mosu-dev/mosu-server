package life.mosu.mosuserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 파일 관련 에러
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다."),

    // FAQ 관련 에러
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ를 찾을 수 없습니다."),
    FAQ_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FAQ 등록에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
