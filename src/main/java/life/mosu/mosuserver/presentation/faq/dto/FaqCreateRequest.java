package life.mosu.mosuserver.presentation.faq.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public record FaqCreateRequest(

    @NotNull String question,
    @NotNull String answer,
    Long userId,
    @RequestParam("file") List<MultipartFile> file

) {
    public FaqJpaEntity toEntity() {
        return FaqJpaEntity.builder()
            .question(question)
            .answer(answer)
            .userId(userId)
            .build();
    }

    public FaqAttachmentJpaEntity toAttachmentEntity(String fileName, String s3Key, Long faqId) {
        return FaqAttachmentJpaEntity.builder()
            .fileName(fileName)
            .s3Key(s3Key)
            .visibility(Visibility.PUBLIC)
            .faqId(faqId)
            .build();
    }
}
