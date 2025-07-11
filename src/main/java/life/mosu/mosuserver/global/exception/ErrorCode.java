package life.mosu.mosuserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Principal 관련 에러
    PRINCIPAL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "인증된 사용자를 찾을 수 없습니다."),

    // OAuth 관련 에러
    UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth2 제공자입니다."),
    OAUTH_USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 OAuth 사용자입니다."),
    FAILED_TO_GET_KAKAO_OAUTH_USER(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth 사용자 생성에 실패했습니다."),

    // Auth 관련 에러
    INCORRECT_ID_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 타입입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    NOT_EXIST_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 존재하지 않습니다."),

    // 유저 관련 에러
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // 신청 관련 에러
    WRONG_SUBJECT_TYPE(HttpStatus.BAD_REQUEST, "잘못된 과목명 입니다."),
    WRONG_LUNCH_TYPE(HttpStatus.BAD_REQUEST, "잘못된 도시락명 입니다."),
    WRONG_AREA_TYPE(HttpStatus.BAD_REQUEST, "잘못된 지역명 입니다."),

    // 신청 학교 관련 에러
    APPLICATION_SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "신청한 학교 정보를 찾을 수 없습니다."),
    APPLICATION_SCHOOL_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "신청한 학교 목록을 찾을 수 없습니다."),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "신청 정보를 찾을 수 없습니다."),
    APPLICATION_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "신청 내역을 찾을 수 없습니다."),
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "학교가 존재하지 않습니다."),
    SCHOOL_FULL(HttpStatus.CONFLICT, "해당 학교의 신청 정원이 모두 찼습니다."),
    APPLICATION_SCHOOL_ALREADY_APPLIED(HttpStatus.CONFLICT, "해당 학교를 이미 예약하였습니다."),

    // 프로필 관련 에러
    PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, "프로필이 이미 존재합니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다."),
    PROFILE_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST, "프로필이 존재하지 않습니다."),
    PROFILE_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 등록에 실패했습니다."),
    PROFILE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 수정에 실패했습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "유효하지 않은 성별 값입니다."),
    ALREADY_REGISTERED_RECOMMENDER(HttpStatus.CONFLICT, "이미 추천인을 등록하였습니다."),

    // 파일 관련 에러
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다."),
    WRONG_FOLDER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 폴더명 입니다."),

    // 이벤트 관련 에러
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이벤트를 찾을 수 없습니다."),

    // FAQ 관련 에러
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ를 찾을 수 없습니다."),
    FAQ_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FAQ 등록에 실패했습니다."),

    // 서버 관련 에러
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // 엑셀 생성 관련 에러
    EXCEL_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "엑셀 생성에 실패했습니다."),

    // 문의 관련 에러
    INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "문의 내역을 찾을 수 없습니다."),
    INQUIRY_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "문의 답변을 찾을 수 없습니다."),
    INQUIRY_ANSWER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 문의 답변이 존재합니다."),

    // 공지 관련 에러
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}
