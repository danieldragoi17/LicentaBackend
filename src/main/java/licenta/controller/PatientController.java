package licenta.controller;

import licenta.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Resource
    private PatientService patientService;

    @GetMapping("/get-last-consultation/{id}")
    public ResponseEntity<?> findAnalysis(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.getLastConsultation(id), HttpStatus.OK);
    }
}
