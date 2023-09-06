package licenta.repository;

import licenta.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis,Long> {

    List<Analysis> findByIdPacient(Long idPacient);

}
