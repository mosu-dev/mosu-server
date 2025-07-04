package life.mosu.mosuserver.persistence.application;

import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<ApplicationJpaEntity, Long> {

    //Optional<ApplicationJpaEntity> findAllByUserId(Long userId);


}
