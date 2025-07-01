package life.mosu.mosuserver.domain.faq;


import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqJpaEntity, Long> {
}
