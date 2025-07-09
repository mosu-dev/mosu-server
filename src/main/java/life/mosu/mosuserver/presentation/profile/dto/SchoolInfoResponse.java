package life.mosu.mosuserver.presentation.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;

@Schema(description = "학교 정보 응답 DTO")
public record SchoolInfoResponse(

        @Schema(description = "학교 이름", example = "서울고등학교")
        String schoolName,

        @Schema(description = "우편번호", example = "12345")
        String zipcode,

        @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
        String street

) {

    public static SchoolInfoResponse from(SchoolInfoJpaVO schoolInfo) {
        if (schoolInfo == null) {
            return null;
        }
        return new SchoolInfoResponse(
                schoolInfo.getSchoolName(),
                schoolInfo.getZipcode(),
                schoolInfo.getStreet()
        );
    }
}
