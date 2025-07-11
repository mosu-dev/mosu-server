package life.mosu.mosuserver.domain.inquiry;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAttachmentJpaRepository extends
        JpaRepository<InquiryAttachmentJpaEntity, Long> {

    List<InquiryAttachmentJpaEntity> findAllByInquiryId(Long id);
}
