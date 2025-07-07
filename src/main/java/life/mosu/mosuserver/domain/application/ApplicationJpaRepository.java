package life.mosu.mosuserver.domain.application;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, Long> {

    List<ApplicationJpaEntity> findAllByUserId(Long userId);


}
