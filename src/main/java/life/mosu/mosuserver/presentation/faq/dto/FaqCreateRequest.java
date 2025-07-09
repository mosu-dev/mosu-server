package life.mosu.mosuserver.presentation.faq.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record FaqCreateRequest(

        @NotNull String question,
        @NotNull String answer,
        @NotNull String author,
        Long userId,
        List<FileRequest> attachments

) {

    public FaqJpaEntity toEntity() {
        return FaqJpaEntity.builder()
                .question(question)
                .answer(answer)
                .userId(userId)
                .author(author)
                .build();
    }


}
