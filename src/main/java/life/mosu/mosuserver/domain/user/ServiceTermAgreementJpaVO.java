package life.mosu.mosuserver.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermAgreementJpaVO {

    @NotNull
    @Column(name = "service_term_tag")
    private String tag;

    @NotNull
    @Column(name = "agreed")
    private boolean agreed;

    @NotNull
    @Column(name = "agreed_at")
    private LocalDateTime agreedAt;
}
