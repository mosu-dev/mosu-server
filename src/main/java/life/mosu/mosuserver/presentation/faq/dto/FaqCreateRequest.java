package life.mosu.mosuserver.presentation.faq.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;

public record FaqCreateRequest(

    @NotNull String question,
    @NotNull String answer,
    Long userId,
    List<FileRequest> attachments

) {
    public FaqJpaEntity toEntity() {
        return FaqJpaEntity.builder()
            .question(question)
            .answer(answer)
            .userId(userId)
            .build();
    }

}
