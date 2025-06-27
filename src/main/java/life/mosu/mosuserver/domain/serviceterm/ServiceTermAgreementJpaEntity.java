package life.mosu.mosuserver.domain.serviceterm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "service_term_agreement")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermAgreementJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "service_term_id")
    private Long tagId;

    @Column(name = "agreed")
    private boolean agreed;

    @Column(name = "agreed_at")
    private LocalDateTime agreedAt;
}
