package life.mosu.mosuserver.domain.notice;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachmentJpaEntity, Long> {

    List<NoticeAttachmentJpaEntity> findAllByNoticeId(Long id);
}
