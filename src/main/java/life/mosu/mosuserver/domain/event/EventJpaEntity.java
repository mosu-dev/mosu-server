package life.mosu.mosuserver.domain.event;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long id;

    @Column(name = "event_title", nullable = false)
    private String title;

    @Embedded
    private DurationJpaVO duration;

    @Column(name = "event_link")
    private String eventLink;

    @Builder
    public EventJpaEntity(
            final String title,
            final DurationJpaVO duration,
            final String eventLink
    ) {
        this.title = title;
        this.duration = duration;
        this.eventLink = eventLink;
    }

    public void update(final String title, final DurationJpaVO duration, final String eventLink) {
        this.title = title;
        this.duration = duration;
        this.eventLink = eventLink;
    }

}
