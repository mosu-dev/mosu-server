package life.mosu.mosuserver.presentation.admin.dto;

import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;

public record StudentFilter(
        String name,
        @PhoneNumberPattern String phone,
        String order
) {

    public StudentFilter {
        if (order == null || order.isBlank()) {
            order = "desc";
        }
    }
}