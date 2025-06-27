package life.mosu.mosuserver.infra.storage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="admission_ticket_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdmissionTicketFileEntity extends File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Builder
    public AdmissionTicketFileEntity(Long userId, String fileName, String s3Key, Visibility visibility) {
        super(fileName, s3Key, visibility);
        this.userId = userId;
    }
}
