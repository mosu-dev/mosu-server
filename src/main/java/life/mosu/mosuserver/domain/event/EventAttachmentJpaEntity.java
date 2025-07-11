package life.mosu.mosuserver.domain.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "event_attachment")
public class EventAttachmentJpaEntity extends File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_attachment_id", nullable = false)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Builder
    public EventAttachmentJpaEntity(final String fileName, final String s3Key,
            final Visibility visibility, final Long eventId) {
        super(fileName, s3Key, visibility);
        this.eventId = eventId;
    }

}
