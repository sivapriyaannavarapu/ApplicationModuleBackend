package com.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.ApplicationConfirmationDto;
import com.application.dto.BatchDatesResponse;
import com.application.dto.CampusAndZoneDTO;
import com.application.dto.ConcessionDTO;
import com.application.dto.EmployeeDetailsDTO;
import com.application.dto.StudentDetailsDTO;
import com.application.entity.AcademicYear;
import com.application.entity.CmpsCourseTrack;
import com.application.entity.ConcessionReason;
import com.application.entity.ConcessionType;
import com.application.entity.CourseBatch;
import com.application.entity.CourseTrack;
import com.application.entity.Employee;
import com.application.entity.ExamProgram;
import com.application.entity.PaymentDetails;
import com.application.entity.ProgramName;
import com.application.entity.Section;
import com.application.entity.Stream;
import com.application.entity.StudentAcademicDetails;
import com.application.entity.StudentConcessionType;
import com.application.entity.StudentPersonalDetails;
import com.application.repository.AcademicYearRepository;
import com.application.repository.CmpsCourseTrackRepository;
import com.application.repository.ConcessionReasonRepository;
import com.application.repository.ConcessionTypeRepository;
import com.application.repository.CourseBatchRepository;
import com.application.repository.CourseTrackRepository;
import com.application.repository.ExamProgramRepository;
import com.application.repository.PaymentDetailsRepository;
import com.application.repository.ProgramNameRepository;
import com.application.repository.SectionRepository;
import com.application.repository.StreamRepository;
import com.application.repository.StudentAcademicDetailsRepository;
import com.application.repository.StudentConcessionTypeRepository;
import com.application.repository.StudentPersonalDetailsRepository;

import jakarta.transaction.Transactional;

@Service
public class ApplicationConfirmationService {

    @Autowired private StudentAcademicDetailsRepository academicRepo;
    @Autowired private StudentPersonalDetailsRepository personalRepo;
    @Autowired private StudentConcessionTypeRepository concessionRepo;
    @Autowired private ConcessionTypeRepository concessionTypeRepo;
    @Autowired private ConcessionReasonRepository concessionReasonRepo;
    @Autowired private PaymentDetailsRepository paymentDetailsRepository;

    // Dropdown repositories
    @Autowired private AcademicYearRepository academicYearRepo;
    @Autowired private StreamRepository streamRepo;
    @Autowired private ProgramNameRepository programRepo;
    @Autowired private ExamProgramRepository examProgramRepo;
    @Autowired private CourseTrackRepository courseTrackRepo;
    @Autowired private CourseBatchRepository courseBatchRepo;
    @Autowired private SectionRepository sectionRepo;
    @Autowired private CmpsCourseTrackRepository cmpsCourseTrackRepo;

    public List<AcademicYear> getJoinYears() {
        return academicYearRepo.findAll();
    }

    public List<Stream> getStreams() {
        return streamRepo.findAll();
    }

    public List<ProgramName> getPrograms() {
        return programRepo.findAll();
    }

    public List<ExamProgram> getExamPrograms() {
        return examProgramRepo.findAll();
    }

    public List<CourseTrack> getCourseTracks() {
        return courseTrackRepo.findAll();
    }

    public List<CourseBatch> getCourseBatches() {
        return courseBatchRepo.findAll();
    }

    public List<Section> getSections() {
        return sectionRepo.findAll();
    }

    public List<ConcessionReason> getConcessionReasons() {
        return concessionReasonRepo.findAll();
    }

    // ---------------- Auto Populate Methods ---------------- //

    /**
     * Auto-populate course batch start/end dates
     */
    public BatchDatesResponse getCourseBatchDetails(Integer batchId) {
        CourseBatch batch = courseBatchRepo.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Invalid Batch ID: " + batchId));

        return new BatchDatesResponse(batch.getStart_date(), batch.getEnd_date());
    }

    public CampusAndZoneDTO getCampusAndZoneByAdmissionNo(String admissionNo) {
        // Use the correct repository method which returns an Optional
        return academicRepo.findByStudAdmsNo(admissionNo)
            .map(studentDetails -> {
                // Check if both campus and its associated zone exist
                if (studentDetails.getCampus() != null && studentDetails.getCampus().getZone() != null) {
                    // Create and populate the DTO
                    CampusAndZoneDTO dto = new CampusAndZoneDTO();
                    dto.setCampusId(studentDetails.getCampus().getCampusId());
                    dto.setCampusName(studentDetails.getCampus().getCampusName());
                    dto.setZoneId(studentDetails.getCampus().getZone().getZoneId());
                    dto.setZoneName(studentDetails.getCampus().getZone().getZoneName());
                    return dto;
                }
                return null; // Return null if campus or zone is not available
            })
            .orElse(null); // Return null if student is not found
    }
    
    public Optional<Float> getCourseFeeByDetails(int cmpsId, int courseTrackId, int courseBatchId) {
        Optional<CmpsCourseTrack> cmpsCourseTrack = cmpsCourseTrackRepo.findByCmpsIdAndCourseTrack_CourseTrackIdAndCourseBatch_CourseBatchId(
            cmpsId, courseTrackId, courseBatchId
        );

        return cmpsCourseTrack.map(CmpsCourseTrack::getCourse_fee);
    }
    
    public Optional<StudentDetailsDTO> getStudentDetailsByAdmissionNo(String admissionNo) {
        // Step 1: Find StudentAcademicDetails
        Optional<StudentAcademicDetails> academicDetailsOptional = academicRepo.findByStudAdmsNo(admissionNo);

        if (academicDetailsOptional.isEmpty()) {
            return Optional.empty();
        }

        StudentAcademicDetails academicDetails = academicDetailsOptional.get();
        int studAdmsId = academicDetails.getStud_adms_id();

        // Step 2 & 3: Fetch other details and handle potential nulls
        Optional<StudentPersonalDetails> personalDetailsOptional = personalRepo.findByStudentAcademicDetails(academicDetails);
        Optional<PaymentDetails> paymentDetailsOptional = paymentDetailsRepository.findByStudentAcademicDetails(academicDetails);
        List<StudentConcessionType> concessions = concessionRepo.findByStudAdmsId(studAdmsId);

        // Populate the DTO
        StudentDetailsDTO dto = new StudentDetailsDTO();
        
        dto.setStudentName(academicDetails.getFirst_name());
        dto.setSurname(academicDetails.getLast_name());
        dto.setGender(academicDetails.getGender() != null ? academicDetails.getGender().getGenderName() : "N/A");

        personalDetailsOptional.ifPresent(personal -> {
            dto.setParentName(personal.getFather_name() + " " + personal.getMother_name());
        });

        paymentDetailsOptional.ifPresent(payment -> {
            dto.setApplicationFee(payment.getApp_fee());
            dto.setConfirmationAmount(payment.getPaid_amount());
        });
        
        // Populate concessions
        dto.setConcessionAmounts(concessions.stream()
                .map(StudentConcessionType::getConc_amount)
                .collect(Collectors.toList()));

        return Optional.of(dto);
    }

    


    // ---------------- Save/Update Admission ---------------- //

    /**
     * Save or update admission details along with concessions
     */
    @Transactional
    public void saveOrUpdateAdmission(ApplicationConfirmationDto dto) {
        StudentAcademicDetails academicDetails = academicRepo
                .findByStudAdmsNo(dto.getAdmissionNo())
                .orElseThrow(() -> new RuntimeException("Invalid Admission No: " + dto.getAdmissionNo()));

        // Save or update concessions (3 rows: 1st, 2nd, 3rd year)
        saveOrUpdateConcessions(dto, academicDetails);

        // Update dropdown selections (foreign keys)
        updateAcademicDetails(dto, academicDetails);

        academicRepo.save(academicDetails);
    }

    /**
     * Save or update concessions for 1st, 2nd, and 3rd year without deletion
     */
    private void saveOrUpdateConcessions(ApplicationConfirmationDto dto, StudentAcademicDetails academicDetails) {
        if (dto.getConcessions() == null || dto.getConcessions().isEmpty()) {
            return;
        }

        // For each concession in the DTO, find if it exists and update, or create new
        for (ConcessionDTO conc : dto.getConcessions()) {
            // Try to find existing concession for this student and concession type
        	Optional<StudentConcessionType> existingConcession = findConcessionByStudentAndType(
        	        academicDetails.getStud_adms_id(), conc.getConcessionTypeId());

            StudentConcessionType entity;
            
            if (existingConcession.isPresent()) {
                // Update existing concession
                entity = existingConcession.get();
                entity.setConc_amount(conc.getConcessionAmount().floatValue());

                // FIX: Handle null check for concession reason
                ConcessionReason currentReason = entity.getConcessionReason();
                if (currentReason != null && currentReason.getConc_reason_id() != conc.getReasonId()) { // Changed equals to !=
                    ConcessionReason reason = concessionReasonRepo.findById(conc.getReasonId())
                            .orElseThrow(() -> new RuntimeException("Invalid Concession Reason ID: " + conc.getReasonId()));
                    entity.setConcessionReason(reason);
                } else if (currentReason == null) {
                    ConcessionReason reason = concessionReasonRepo.findById(conc.getReasonId())
                            .orElseThrow(() -> new RuntimeException("Invalid Concession Reason ID: " + conc.getReasonId()));
                    entity.setConcessionReason(reason);
                }
            }else {
                // Create new concession
                entity = new StudentConcessionType();
                entity.setStudAdmsId(academicDetails.getStud_adms_id());
                entity.setAcadId(getAcademicYearIdForConcession(conc.getConcessionTypeId()));
                entity.setConc_amount(conc.getConcessionAmount().floatValue());
                
                // Set concession type (1st, 2nd, 3rd year)
                ConcessionType concessionType = concessionTypeRepo
                        .findById(conc.getConcessionTypeId())
                        .orElseThrow(() -> new RuntimeException("Concession type not found for ID: " + conc.getConcessionTypeId()));
                
                entity.setConcessionType(concessionType);
                
                // Set concession reason
                ConcessionReason reason = concessionReasonRepo.findById(conc.getReasonId())
                        .orElseThrow(() -> new RuntimeException("Invalid Concession Reason ID: " + conc.getReasonId()));
                entity.setConcessionReason(reason);
            }

            concessionRepo.save(entity);
        }
    }

    /**
     * Update academic details with form selections
     */
    private void updateAcademicDetails(ApplicationConfirmationDto dto, StudentAcademicDetails academicDetails) {
        if (dto.getJoinYearId() != null) {
            academicDetails.setAcademicYear(academicYearRepo.findById(dto.getJoinYearId())
                    .orElseThrow(() -> new RuntimeException("Invalid Join Year ID: " + dto.getJoinYearId())));
        }
        if (dto.getStreamId() != null) {
            academicDetails.setStream(streamRepo.findById(dto.getStreamId())
                    .orElseThrow(() -> new RuntimeException("Invalid Stream ID: " + dto.getStreamId())));
        }
        if (dto.getProgramId() != null) {
            academicDetails.setProgramName(programRepo.findById(dto.getProgramId())
                    .orElseThrow(() -> new RuntimeException("Invalid Program ID: " + dto.getProgramId())));
        }
        if (dto.getExamProgramId() != null) {
            academicDetails.setExamProgram(examProgramRepo.findById(dto.getExamProgramId())
                    .orElseThrow(() -> new RuntimeException("Invalid Exam Program ID: " + dto.getExamProgramId())));
        }
        if (dto.getCourseTrackId() != null) {
            academicDetails.setCmpsCourseTrack(cmpsCourseTrackRepo.findById(dto.getCourseTrackId())
                    .orElseThrow(() -> new RuntimeException("Invalid Course Track ID: " + dto.getCourseTrackId())));
        }
        if (dto.getBatchId() != null) {
            academicDetails.setCourse_batch_id(dto.getBatchId());
        }
        if (dto.getSectionId() != null) {
            academicDetails.setSection(sectionRepo.findById(dto.getSectionId())
                    .orElseThrow(() -> new RuntimeException("Invalid Section ID: " + dto.getSectionId())));
        }
        if (dto.getApp_conf_date() != null) {
            academicDetails.setApp_conf_date(dto.getApp_conf_date());
        }
    }

    // Helper method to get academic year ID based on concession type ID
    private Integer getAcademicYearIdForConcession(Integer concessionTypeId) {
        // Implement logic to map concession type ID to academic year ID
        // This will depend on your database structure
        // For example, if concessionTypeId 1 = 1st year, 2 = 2nd year, 3 = 3rd year
        return concessionTypeId; // This is just an example - adjust based on your actual mapping
    }

    // Alternative method if the repository query still has issues
    private Optional<StudentConcessionType> findConcessionByStudentAndType(Integer studAdmsId, Integer concessionTypeId) {
        return concessionRepo.findByStudAdmsId(studAdmsId).stream()
                .filter(conc -> conc.getConcessionType() != null 
                        && Integer.valueOf(conc.getConcessionType().getConcTypeId()).equals(concessionTypeId))
                .findFirst();
    }
    
    public Optional<EmployeeDetailsDTO> getEmployeeDetailsByAdmissionNo(String admissionNo) {
        // Step 1: Find the StudentAcademicDetails entity by admission number
        Optional<StudentAcademicDetails> academicDetailsOptional = academicRepo.findByStudAdmsNo(admissionNo);

        if (academicDetailsOptional.isEmpty()) {
            return Optional.empty(); // Return empty if student not found
        }

        StudentAcademicDetails academicDetails = academicDetailsOptional.get();

        // Step 2: Access the Employee (pro_id) from the academic details
        Employee employee = academicDetails.getEmployee();

        // Step 3: Check if the employee is not null before proceeding
        if (employee == null) {
            return Optional.empty(); // Return empty if no employee is associated
        }

        // Step 4: Create and populate the DTO
        EmployeeDetailsDTO dto = new EmployeeDetailsDTO();
        dto.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());
        dto.setEmployeeMobileNo(employee.getPrimary_mobile_no());
        
        // Step 5: Access the Campus from the Employee entity
        if (employee.getCampus() != null) {
            dto.setCampusName(employee.getCampus().getCampusName());
        }

        return Optional.of(dto);
    }
}


