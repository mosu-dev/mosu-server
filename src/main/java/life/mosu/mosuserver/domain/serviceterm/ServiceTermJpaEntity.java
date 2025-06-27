package life.mosu.mosuserver.domain.serviceterm;


import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "service_term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ServiceTermJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_term_id")
    private String id;

    @Column(name = "service_term_tag")
    private String tag;

    @Column(name = "required")
    private boolean required;

    @Column(name = "revocable")
    private boolean revocable;

    @Builder
    public ServiceTermJpaEntity(
        final String tag,
        final boolean required,
        final boolean revocable
    ) {
        this.tag = tag;
        this.required = required;
        this.revocable = revocable;
    }
}
