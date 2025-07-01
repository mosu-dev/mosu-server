package life.mosu.mosuserver.presentation.profile.dto;

import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;

public record SchoolInfoResponse(
    String schoolName,
    String zipcode,
    String street
) {
    public static SchoolInfoResponse from(SchoolInfoJpaVO schoolInfo) {
        if (schoolInfo == null) return null;
        return new SchoolInfoResponse(
            schoolInfo.getSchoolName(),
            schoolInfo.getZipcode(),
            schoolInfo.getStreet()
        );
    }
}