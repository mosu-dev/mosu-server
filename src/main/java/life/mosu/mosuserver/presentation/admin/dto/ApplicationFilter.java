package life.mosu.mosuserver.presentation.admin.dto;

import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;

import java.time.LocalDate;

public record ApplicationFilter(
    String name,
    @PhoneNumberPattern String phone,
    String schoolName,
    LocalDate applicationDate
) {

}
