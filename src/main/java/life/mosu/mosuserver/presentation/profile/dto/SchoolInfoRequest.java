package life.mosu.mosuserver.presentation.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;

@Schema(description = "학교 정보 요청 DTO")
public record SchoolInfoRequest(

        @Schema(description = "학교 이름", example = "서울고등학교")
        String schoolName,

        @Schema(description = "우편번호", example = "12345")
        String zipcode,

        @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
        String street

) {

    public SchoolInfoJpaVO toEntity() {
        return new SchoolInfoJpaVO(schoolName, zipcode, street);
    }
}
