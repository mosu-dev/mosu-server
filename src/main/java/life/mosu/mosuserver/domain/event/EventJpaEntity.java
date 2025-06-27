package life.mosu.mosuserver.domain.event;

import jakarta.persistence.*;
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

    @Column(name = "event_description", nullable = false)
    private String eventDescription;

    @Column(name = "event_link")
    private String eventLink;

    @Column(name = "event_img")
    private String eventImg;

    @Embedded
    private DurationJpaVO duration;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public EventJpaEntity(
        final String eventDescription,
        final String eventLink,
        final String eventImg,
        final Long userId
    ) {
        this.eventDescription = eventDescription;
        this.eventLink = eventLink;
        this.eventImg = eventImg;
        this.userId = userId;
    }
}
