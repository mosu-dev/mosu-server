package life.mosu.mosuserver.persistence.application;

import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, Long> {

    List<ApplicationJpaEntity> findAllByUserId(Long userId);


}
