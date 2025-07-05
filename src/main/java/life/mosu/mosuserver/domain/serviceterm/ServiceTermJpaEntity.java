package life.mosu.mosuserver.domain.serviceterm;


import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "service_term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ServiceTermJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_term_id")
    private Long id;

    @Column(name = "service_term_tag")
    private String tag;

    @Column(name = "required")
    private boolean required;

    @Builder
    public ServiceTermJpaEntity(
        final String tag,
        final boolean required

    ) {
        this.tag = tag;
        this.required = required;
    }
}
