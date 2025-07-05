package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.school.AddressJpaVO;

public record AddressRequest(
    String zipcode,
    String street,
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