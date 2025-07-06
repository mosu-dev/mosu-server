package life.mosu.mosuserver.domain.application;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, Long> {

    @Query("SELECT COUNT(a) = :size FROM ApplicationJpaEntity a WHERE a.id IN :applicationIds")
    boolean existsAllByIds(@Param("applicationIds") List<Long> applicationIds,
            @Param("size") long size);
  
    List<ApplicationJpaEntity> findAllByUserId(Long userId);
}
