package com.application.service;


import com.application.dto.ApplicationConfirmationDto;
import com.application.entity.*;
import com.application.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationConfirmationService {

    @Autowired private StudentAcademicDetailsRepository academicRepo;
    @Autowired private StudentPersonalDetailsRepository personalRepo;
    @Autowired private PaymentDetailsRepository paymentRepo;
    @Autowired private CourseBatchRepository courseBatchRepo;
    @Autowired private CmpsCourseTrackRepository cmpsCourseTrackRepo;
    @Autowired private AcademicYearRepository academicYearRepo;
    @Autowired private StreamRepository streamRepo;
    @Autowired private ProgramNameRepository programRepo;
    @Autowired private ExamProgramRepository examProgramRepo;
    @Autowired private CourseTrackRepository courseTrackRepo;
    @Autowired private SectionRepository sectionRepo;
    @Autowired private ConcessionTypeRepository concessionTypeRepo;
    @Autowired private ConcessionReasonRepository concessionReasonRepo;
    
 // -------- DROPDOWNS --------
    public List<AcademicYear> getAcademicYears() { return academicYearRepo.findAll(); }
    public List<Stream> getStreams() { return streamRepo.findAll(); }
    public List<ProgramName> getPrograms() { return programRepo.findAll(); }
    public List<ExamProgram> getExamPrograms() { return examProgramRepo.findAll(); }
    public List<CourseTrack> getCourseTracks() { return courseTrackRepo.findAll(); }
    public List<CourseBatch> getCourseBatches() { return courseBatchRepo.findAll(); }
    public List<Section> getSections() { return sectionRepo.findAll(); }
    public List<ConcessionType> getConcessionTypes() { return concessionTypeRepo.findAll(); }
    public List<ConcessionReason> getConcessionReasons() { return concessionReasonRepo.findAll(); }

    // -------- SAVE / UPDATE --------
    @Transactional
    public ApplicationConfirmationDto saveOrUpdate(ApplicationConfirmationDto dto) {

        Optional<StudentAcademicDetails> existing = academicRepo.findByStudAdmsNo(dto.getAdmissionNumber());
        StudentAcademicDetails academicDetails = existing.orElse(new StudentAcademicDetails());
        academicDetails.setStud_adms_no(dto.getAdmissionNumber());
        academicDetails.setFirst_name(dto.getFirstName());
        academicDetails.setLast_name(dto.getLastName());
        academicDetails.setCourse_track_id(dto.getCourseTrackId() != null ? dto.getCourseTrackId() : 0);
        academicDetails.setCourse_batch(dto.getCourseBatchId() != null ? dto.getCourseBatchId() : 0);
        academicRepo.save(academicDetails);

        // Personal details
        StudentPersonalDetails personal = personalRepo
                .findByStudentAcademicDetails(academicDetails)
                .orElse(new StudentPersonalDetails());
        personal.setFather_name(dto.getParentName());
        personal.setStudentAcademicDetails(academicDetails);
        personalRepo.save(personal);

        // Payment details
        PaymentDetails payment = paymentRepo
                .findByStudentAcademicDetails(academicDetails)
                .orElse(new PaymentDetails());
        payment.setApp_fee(dto.getAppFee());
        payment.setConc_amount(dto.getConcAmount());
//        payment.setFirstYearConcession(dto.getFirstYearConcession());
//        payment.setSecondYearConcession(dto.getSecondYearConcession());
//        payment.setThirdYearConcession(dto.getThirdYearConcession());
        payment.setStudentAcademicDetails(academicDetails);
        paymentRepo.save(payment);

        // Autopopulate from batch
        if (dto.getCourseBatchId() != null) {	
            courseBatchRepo.findById(dto.getCourseBatchId()).ifPresent(batch -> {
                dto.setCourseStartDate(batch.getStart_date());
                dto.setCourseEndDate(batch.getEnd_date());
            });
        }

        // Autopopulate from course track
        if (dto.getCourseTrackId() != null) {
            cmpsCourseTrackRepo.findByCourseTrack_CourseTrackId(dto.getCourseTrackId())
                    .ifPresent(track -> dto.setCourseFee((float) track.getCourse_fee()));
        }

        return dto;
    }

    // -------- GET by Admission No --------
    @Transactional
    public ApplicationConfirmationDto getByAdmissionNo(String admissionNo) {
        ApplicationConfirmationDto dto = new ApplicationConfirmationDto();

        Optional<StudentAcademicDetails> academicOpt = academicRepo.findByStudAdmsNo(admissionNo);
        if (academicOpt.isEmpty()) {
            return null;
        }
        StudentAcademicDetails academic = academicOpt.get();
        dto.setAdmissionNumber(academic.getStud_adms_no());
        dto.setFirstName(academic.getFirst_name());
        dto.setLastName(academic.getLast_name());
        if (academic.getGender() != null) {
            dto.setGender(academic.getGender().getGenderName()); // assumes Gender has genderName
        }

        // Personal details
        personalRepo.findByStudentAcademicDetails(academic).ifPresent(personal -> {
            dto.setParentName(personal.getFather_name());
        });

//        paymentRepo.findByStudentAcademicDetails(academic).ifPresent(payment -> {
//            dto.setApplicationFee(payment.getAppFee());
//            dto.setConfirmationAmount(payment.getConcAmount());
//            dto.setFirstYearConcession(payment.getFirstYearConcession());
//            dto.setSecondYearConcession(payment.getSecondYearConcession());
//            dto.setThirdYearConcession(payment.getThirdYearConcession());
//        });
        paymentRepo.findByStudentAcademicDetails(academic).ifPresent(payment -> {
            dto.setAppFee(payment.getApp_fee());           // maps to entity app_fee
            dto.setConcAmount(payment.getConc_amount());   // maps to conc_amount
                      
        });

        // Course batch dates
        if (academic.getCourse_batch() != 0) {
            courseBatchRepo.findById(academic.getCourse_batch()).ifPresent(batch -> {
                dto.setCourseStartDate(batch.getStart_date());
                dto.setCourseEndDate(batch.getEnd_date());
            });
        }

        // Course fee
        if (academic.getCourse_track_id() != 0) {
            cmpsCourseTrackRepo.findByCourseTrack_CourseTrackId(academic.getCourse_track_id())
                    .ifPresent(track -> dto.setCourseFee((float) track.getCourse_fee()));
        }

        return dto;
    }

   
}
