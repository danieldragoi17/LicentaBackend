package licenta.service;

import licenta.model.Analysis;
import licenta.model.Consultation;
import licenta.repository.AnalysisRepository;
import licenta.repository.ConsultationRepository;
import licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class PatientService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private AnalysisRepository analysisRepository;

    @Resource
    private ConsultationRepository consultationRepository;

    public Consultation getLastConsultation(Long idPatient){
        // first get all the analyses for the pacient
        List<Analysis> analysisList = analysisRepository.findByIdPacient(idPatient);

        // then get all the consultations for these analyses
        List<Consultation> consultationList = new ArrayList<>();
        for (Analysis a : analysisList){
            Consultation c = consultationRepository.findByIdAnaliza(a.getId());
            if (c != null)
                consultationList.add(c);
        }

        consultationList.sort(Comparator.comparing(Consultation::getIdAnaliza).reversed());

        if(consultationList.isEmpty())
            return new Consultation();
        return consultationList.get(0);
    }
}
