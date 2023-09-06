package licenta.repository;

import licenta.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    Consultation findByIdAnaliza(Long idAnalysis);
}
