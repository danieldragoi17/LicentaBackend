package com.example.licenta.controller;

import com.example.licenta.exceptions.StudentNotFoundException;
import com.example.licenta.model.Acord;
import com.example.licenta.model.PracticeDocument;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.Task;
import com.example.licenta.model.dto.GetCoordonatorMessagesRequestDto;
import com.example.licenta.model.dto.PracticeDocumentDTO;
import com.example.licenta.service.CoordonatorService;
import com.example.licenta.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("coordonator")
@CrossOrigin(origins = "*")
public class CoordonatorController {
    @Resource
    private CoordonatorService coordonatorService;
    @Resource
    private UserService userService;

    @PostMapping("/receive-student/{studentId}/{teacherId}")
    public ResponseEntity<?> receiveStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        coordonatorService.receiveStudent(studentId, teacherId);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptRequest(@RequestBody StudentTeacherId studentTeacherId) {
        coordonatorService.acceptRequest(studentTeacherId);
        return new ResponseEntity<>("bine", HttpStatus.ACCEPTED);
    }

    @PostMapping("/acord")
    public ResponseEntity<?> getAcord(@RequestBody StudentTeacherId studentTeacherId) {
        Acord acord = coordonatorService.getAcord(studentTeacherId);
        return new ResponseEntity<>(acord, HttpStatus.ACCEPTED);
    }

    @GetMapping("/acord/{teacherId}")
    public ResponseEntity<?> findAllAcords(@PathVariable Long teacherId) {
        return new ResponseEntity<>(coordonatorService.findAllAcords(teacherId), HttpStatus.OK);
    }

    @GetMapping("/students/{teacherId}")
    public ResponseEntity<?> findAllStudents(@PathVariable Long teacherId) {

        return new ResponseEntity<>(coordonatorService.getStudents(teacherId), HttpStatus.OK);
    }

    @GetMapping("/studentsAcc/{teacherId}")
    public ResponseEntity<?> findAllAccStudents(@PathVariable Long teacherId) {

        return new ResponseEntity<>(coordonatorService.getStudentsAccepted(teacherId), HttpStatus.OK);
    }
    // @GetMapping("/acord")
    // public ResponseEntity<?> findAllAcords() {
    // List<User> users=coordonatorService.getStudents();
    // return new ResponseEntity<>(coordonatorService.findAllAcords(teacherId),
    // HttpStatus.OK);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocuriLibere(@PathVariable Long id) {
        return new ResponseEntity<>(coordonatorService.getLocuriLibere(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTeachers() {
        return new ResponseEntity<>(coordonatorService.getTeachers(), HttpStatus.OK);
    }

    @GetMapping("/interese/{id}")
    public ResponseEntity<?> getThemesInteres(@PathVariable Long id) {
        return new ResponseEntity<>(coordonatorService.getThemesInteres(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateThemesInteres(@PathVariable Long id, @RequestBody String newThemesInteres) {
        coordonatorService.updateThemesInteres(id, newThemesInteres);
        return new ResponseEntity<>("bine", HttpStatus.OK);
    }

    @GetMapping("/acceptedStudents/{id}")
    public ResponseEntity<?> getAcceptedStudents(@PathVariable Long id) {
        return new ResponseEntity<>(coordonatorService.getAcceptedStudents(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<?> getContacts(@PathVariable Long id) {
        return new ResponseEntity<>(coordonatorService.getCoordonatorContacts(id), HttpStatus.OK);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> getMessages(@RequestBody GetCoordonatorMessagesRequestDto getCoordonatorMessagesRequest) {
        return new ResponseEntity<>(coordonatorService.getCoordonatorMessages(getCoordonatorMessagesRequest.getTeacherId(), getCoordonatorMessagesRequest.getStudentId()), HttpStatus.OK);
    }

    @PostMapping("/practiceDocument")
    public ResponseEntity<?> savePracticeDocument(@RequestBody PracticeDocumentDTO practiceDocumentDTO)
            throws MalformedURLException {
        List<PracticeDocument> response = new ArrayList<>();
        for (File document : practiceDocumentDTO.getFileList()) {
            PracticeDocument practiceDocument = new PracticeDocument(123L,
                    userService.findById(practiceDocumentDTO.getUserId()), document.toURI().toURL().toString());
            response.add(coordonatorService.savePracticeDocument(practiceDocument));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assignment")
    public ResponseEntity<?> saveAssignment(@RequestBody Task task) throws MalformedURLException {
        System.out.println("intru");
        return ResponseEntity.ok(coordonatorService.saveAssignment(task));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTasks(@PathVariable Long id)
    {
        return new ResponseEntity<>(coordonatorService.getTasks(id),HttpStatus.OK);
    }

    @GetMapping("/announcement")
    public ResponseEntity<?> getAnnouncement() {
        return new ResponseEntity<>(coordonatorService.getAnnouncements(), HttpStatus.OK);
    }

}