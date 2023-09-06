package licenta.controller;

import licenta.model.Analysis;
import licenta.model.Consultation;
import licenta.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    @GetMapping("/get-patients")
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(doctorService.getAllPatients(), HttpStatus.OK);
    }

    @PostMapping("/add-analysis")
    public ResponseEntity<?> addAnalysis(@RequestBody Analysis analysis){
        try{
            System.out.println("Save analysis, name: " + analysis.getName());
            doctorService.addAnalysis(analysis);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Eroare la datele pentru analiza!!");
        }
    }

    @PostMapping("/add-consultation")
    public ResponseEntity<?> addConsultation(@RequestBody Consultation consultation){
        try{
            System.out.println("Save consultation, name: " + consultation.getDetails());
            doctorService.addConsultation(consultation);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Eroare la datele pentru analiza!!");
        }
    }

    @GetMapping("/get-consultation/{id}")
    public ResponseEntity<?> findConsultation(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.getConsultation(id), HttpStatus.OK);
    }

    @GetMapping("/get-analysis/{id}")
    public ResponseEntity<?> findAnalysis(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.getAnalysis(id), HttpStatus.OK);
    }
}
