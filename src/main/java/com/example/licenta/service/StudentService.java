package com.example.licenta.service;

import com.example.licenta.exceptions.NoCoordinatorException;
import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.exceptions.StudentNotFoundException;
import com.example.licenta.model.*;
import com.example.licenta.model.dto.*;
import com.example.licenta.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Resource
    AcordRepository acordRepository;
    @Resource
    CoordonationRepository coordonationRepository;
    @Resource
    TeacherRepository teacherRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    AnnouncementRepository announcementRepository;
    @Resource
    GlobalDetailsRepository globalDetailsRepository;
    @Resource
    StudentRepository studentRepository;
    @Resource
    TaskRepository taskRepository;
    @Resource
    AdminAnnouncementRepository adminAnnouncementRepository;

    @Resource
    MessageRepository messageRepository;

    public List<StudentDetails> getStudents() {
        return studentRepository.findAll();
    }

    public List<StudentDetails> getStudentsWithoutCoordinator() {
        List<StudentDetails> studentDetailsList = new ArrayList<>();
        for (StudentDetails studentDetails : getStudents()) {
            if (!isCoordinated(studentDetails.getUser().getId()))
                studentDetailsList.add(studentDetails);
        }
        return studentDetailsList;
    }

    private Long getNumberOfSentRequests(Long studentId) {
        return acordRepository
                .findAll()
                .stream()
                .filter(x -> x.getTime().isAfter(LocalDateTime.now().minusWeeks(1))
                        &&
                        x.getId().getStudentId() == studentId)
                .count();
    }

    public void sendRequest(Acord solicitareAcordRequest) throws RequestsLimitReachedException {
        Long countOfRequestsMade = getNumberOfSentRequests(solicitareAcordRequest.getId().getStudentId());
        if(isCoordinated(solicitareAcordRequest.getId().getStudentId()))
            throw new RequestsLimitReachedException("This student already has a coordinator.");
        if (countOfRequestsMade >= 3) {
            throw new RequestsLimitReachedException("This student has already made 3 requests this week.");
        }

        StudentTeacherId studentTeacherId = new StudentTeacherId(solicitareAcordRequest.getId().getStudentId(),
                solicitareAcordRequest.getId().getTeacherId());
        String fileURL = solicitareAcordRequest.getDocumentUrl();
        acordRepository.save(new Acord(studentTeacherId, fileURL, LocalDateTime.now()));
    }

    public Long getRequestCount(Long studentId) {
        return getNumberOfSentRequests(studentId);
    }

    public Boolean isCoordinated(Long studentId) {
        return coordonationRepository.existsByStudentId(studentId);
    }

    public TeacherDetails getCoordinatorForStudent(Long studentId) {
        Long teacherId = coordonationRepository.findTeacherIdByStudentId(studentId);
        return teacherRepository.findByUserId(teacherId);
    }

    public List<AdminAnnouncement> getAnnouncements() {
        return adminAnnouncementRepository.findAll();
    }

    public PracticeDetailsDto getLatestDetails(Long studentId) throws StudentNotFoundException {
        PracticeDetailsDto practiceDetailsDto = new PracticeDetailsDto();
        GlobalDetails globalDetails = globalDetailsRepository.findFirstByOrderByIdDesc();
        practiceDetailsDto.setGlobalDetails(globalDetails)  ;

        StudentDetails studentDetails = studentRepository.findByUserId(studentId);

        practiceDetailsDto.setExecutedHours(studentDetails.getExecutedHours());
        practiceDetailsDto.setRemainingHours(globalDetails.getPracticeHoursTotal() - studentDetails.getExecutedHours());

        TeacherDetails teacherDetails = getCoordinatorForStudent(studentId);
        practiceDetailsDto.setCoordonator(teacherDetails.getUser().getFirstName() + " " + teacherDetails.getUser().getLastName());
        practiceDetailsDto.setTasks(taskRepository.findAllByStudentTeacherid(new StudentTeacherId(studentId, teacherDetails.getUser().getId())));

        return practiceDetailsDto;
    }

    public void turnInTask(Long taskId, String documentUrl) {
        Task task = taskRepository.findById(taskId).get();
        task.getDocumentUrls().add(documentUrl);
        taskRepository.save(task);
    }

    public EvaluationDto getEvaluation(Long studentId) {
        EvaluationDto evaluationDto = new EvaluationDto();
        StudentDetails studentDetails = studentRepository.findByUserId(studentId);
        evaluationDto.setNormalGrade(studentDetails.getNormalGrade());
        evaluationDto.setReTakeGrade(studentDetails.getReTakeGrade());

        TeacherDetails teacherDetails = getCoordinatorForStudent(studentId);
        evaluationDto.setEvaluationCriteria(teacherDetails.getPracticeCriteria());
        return evaluationDto;
    }



    public List<StatusCerereDto> getStatusCereri(Long studentId) {
        List<StatusCerereDto> statusDtos = new ArrayList<>();

        List<Acord> acords = acordRepository.findAllById_StudentId(studentId);
        for (Acord acord : acords) {
            StatusCerereDto status = new StatusCerereDto();

            if (coordonationRepository.existsByStudentId(studentId))
                status.setStatusCerereType(StatusCerereType.APPROVED);
            else
                status.setStatusCerereType(StatusCerereType.PENDING);

            Long teacherId = acord.getId().getTeacherId();
            User teacher = userRepository.findAll().stream().filter(user -> teacherId == user.getId()).findAny().orElse(null);
            status.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
            statusDtos.add(status);
        }
        return statusDtos;
    }

    public StudentMessagesResponseDto getMessagesForStudentAndTeacher(Long studentId) throws NoCoordinatorException {
        Optional<User> student = userRepository.findById(studentId);
        TeacherDetails teacher = getCoordinatorForStudent(studentId);
        if (teacher == null){
            throw new NoCoordinatorException("This student doesn't have a coordinator.");
        }
        else {
            List<Message> messages = messageRepository.findMessagesForStudentAndTeacher(studentId, teacher.getUser().getId());
            return new StudentMessagesResponseDto(student.get(),teacher.getUser(), messages);
        }
    }

    public boolean sendMessage(Long fromId, Long toId, String message) {
        messageRepository.save(new Message(fromId, toId, message, LocalDateTime.now()));
        return true;
    }

    public GetAdminMessagesForStudentResponseDto getAdminMessagesForStudent(Long studentId) {
        User student = userRepository.findById(studentId).get();
        Long adminId = userRepository.findAdminAccount().getId();
        List<Message> messages = messageRepository.findMessagesForStudentAndTeacher(studentId, adminId);
        return new GetAdminMessagesForStudentResponseDto(student,adminId,messages);
    }
}
