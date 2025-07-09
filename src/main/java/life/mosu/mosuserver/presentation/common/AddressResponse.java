package life.mosu.mosuserver.presentation.common;

import life.mosu.mosuserver.domain.school.AddressJpaVO;

public record AddressResponse(
        String zipcode,
        String street,
        String detail
) {

    public static AddressResponse from(AddressJpaVO address) {
        return new AddressResponse(
                address.getZipcode(),
                address.getStreet(),
                address.getDetail()
        );
    }
}