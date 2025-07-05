package life.mosu.mosuserver.domain.application;

import jakarta.persistence.*;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;


@Getter
@Entity
@Table(name = "admission_ticket_image")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SoftDelete
public class AdmissionTicketImageJpaEntity extends File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_ticket_image_id", nullable = false)
    private Long id;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Builder
    public AdmissionTicketImageJpaEntity(final String fileName, final String s3Key, final Visibility visibility, final Long applicationId) {
        super(fileName, s3Key, visibility);
        this.applicationId = applicationId;
    }

}
