package life.mosu.mosuserver.domain.notice;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeAttachmentRepository extends JpaRepository<NoticeAttachmentJpaEntity, Long> {

    /**
 * Retrieves all notice attachment entities associated with the specified notice ID.
 *
 * @param id the unique identifier of the notice
 * @return a list of notice attachment entities linked to the given notice ID
 */
List<NoticeAttachmentJpaEntity> findAllByNoticeId(Long id);
}
