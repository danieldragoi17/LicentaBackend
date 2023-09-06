package licenta.service;

import licenta.model.Analysis;
import licenta.model.Consultation;
import licenta.model.RoleEnum;
import licenta.model.UserEntity;
import licenta.model.dto.PacientsAnalysisDTO;
import licenta.repository.AnalysisRepository;
import licenta.repository.ConsultationRepository;
import licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private AnalysisRepository analysisRepository;

    @Resource
    private ConsultationRepository consultationRepository;

    public List<PacientsAnalysisDTO> getAllPatients(){
        List<UserEntity> allUsers = userRepository.findAll();

        // Filter users by role "PACIENT"
        List<UserEntity> patients = allUsers.stream()
                .filter(user -> RoleEnum.PACIENT.equals(user.getRole()))
                .toList();

        // create list of DTOs
        List<PacientsAnalysisDTO> pacientsAnalysisDTOList = new ArrayList<>();

        // create DTO
        for (UserEntity patient : patients){
            System.out.println(patient.getId());
            List<Analysis> analyses = analysisRepository.findByIdPacient(patient.getId());
            System.out.println(analyses.size());
            List<Analysis> filteredAnalyses = analyses.stream()
                    .filter(analysis -> analysis.getIdPacient().equals(patient.getId()))
                    .toList();

            if (analyses.isEmpty()){
                analyses = new ArrayList<>();
            }
            PacientsAnalysisDTO pacientsAnalysisDTO = new PacientsAnalysisDTO(patient.getId(),
                    patient.getFirstname() + " " + patient.getLastname(),
                    "", analyses);
            pacientsAnalysisDTOList.add(pacientsAnalysisDTO);
            System.out.println(patient.getEmail());
        }

        return pacientsAnalysisDTOList;
    }

    public void addAnalysis(Analysis analysis){
        analysisRepository.save(analysis);
    }

    public void addConsultation(Consultation consultation){
        consultationRepository.save(consultation);
    }

    public Consultation getConsultation(Long idAnalysis){
        return consultationRepository.findByIdAnaliza(idAnalysis);
    }

    public Analysis getAnalysis(Long idAnalysis){
        System.out.println("ID ANALIZA: " + idAnalysis);
        Optional<Analysis> optionalAnalysis = analysisRepository.findById(idAnalysis);
        return optionalAnalysis.orElse(null); // Return null if not found, adjust as needed
    }
}
