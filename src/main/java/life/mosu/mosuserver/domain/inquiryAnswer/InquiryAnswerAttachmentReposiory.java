package life.mosu.mosuserver.domain.inquiryAnswer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerAttachmentReposiory extends
        JpaRepository<InquiryAnswerAttachmentEntity, Long> {

    List<InquiryAnswerAttachmentEntity> findAllByInquiryAnswerId(Long id);
}
