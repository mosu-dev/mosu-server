package life.mosu.mosuserver.presentation.applicationschool.dto;

import life.mosu.mosuserver.domain.application.Subject;

import java.util.Set;

public record SubjectUpdateRequest(
    Set<Subject> subjects
) {
}