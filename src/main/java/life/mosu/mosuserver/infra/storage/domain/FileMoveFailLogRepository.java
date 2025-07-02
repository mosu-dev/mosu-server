package life.mosu.mosuserver.infra.storage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMoveFailLogRepository extends JpaRepository<FileMoveFailLog, Long> {

}
