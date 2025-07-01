package life.mosu.mosuserver.presentation.profile.dto;

import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;

public record SchoolInfoRequest(
    String schoolName,
    String zipcode,
    String street
) {
    public SchoolInfoJpaVO toEntity() {
        return new SchoolInfoJpaVO(schoolName, zipcode, street);
    }
}