package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.domain.school.AddressJpaVO;

@Schema(description = "주소 요청 DTO")
public record AddressRequest(

        @Schema(description = "우편번호", example = "12345")
        String zipcode,

        @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
        String street,

        @Schema(description = "상세 주소", example = "202호")
        String detail

) {

    public AddressJpaVO toValueObject() {
        return AddressJpaVO.builder()
                .zipcode(zipcode)
                .street(street)
                .detail(detail)
                .build();
    }
}