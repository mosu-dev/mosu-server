package life.mosu.mosuserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //프로필 관련 에러
    PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, "프로필이 이미 존재합니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다."),
    PROFILE_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST, "프로필이 존재하지 않습니다."),
    PROFILE_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 등록에 실패했습니다."),
    PROFILE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 수정에 실패했습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "유효하지 않은 성별 값입니다."),


    // 파일 관련 에러
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다."),

    // FAQ 관련 에러
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ를 찾을 수 없습니다."),
    FAQ_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FAQ 등록에 실패했습니다."),

    //서버 관련 에러
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

}
