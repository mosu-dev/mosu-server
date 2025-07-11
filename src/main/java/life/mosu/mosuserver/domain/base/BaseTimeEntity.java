package life.mosu.mosuserver.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static String formatDate(java.time.LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate().toString() : null;
    }

    public String getCreatedAt() {
        return createdAt != null ? createdAt.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}