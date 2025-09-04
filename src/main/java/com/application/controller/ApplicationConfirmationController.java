package com.application.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.ApplicationConfirmationDto;
import com.application.dto.BatchDatesResponse;
import com.application.dto.CampusAndZoneDTO;
import com.application.dto.EmployeeDetailsDTO;
import com.application.dto.StudentDetailsDTO;
import com.application.entity.AcademicYear;
import com.application.entity.ConcessionReason;
import com.application.entity.CourseBatch;
import com.application.entity.CourseTrack;
import com.application.entity.ExamProgram;
import com.application.entity.ProgramName;
import com.application.entity.Section;
import com.application.entity.Stream;
import com.application.service.ApplicationConfirmationService;

@RestController
@RequestMapping("/api/application-confirmation")
@CrossOrigin(origins = "*") // Adjust based on your frontend URL
public class ApplicationConfirmationController {
    
    @Autowired
    private ApplicationConfirmationService service;
    
    // ---------------- Save/Update Admission Details ----------------
    @PostMapping("/save")
    public ResponseEntity<String> saveAdmission(@RequestBody ApplicationConfirmationDto dto) {
        try {
            service.saveOrUpdateAdmission(dto);
            return ResponseEntity.ok("Admission details saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
    
    // ---------------- Dropdown Data Endpoints ----------------
    
    @GetMapping("/join-years")
    public ResponseEntity<List<AcademicYear>> getJoinYears() {
        try {
            return ResponseEntity.ok(service.getJoinYears());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/batch/{batchId}/dates")
    public ResponseEntity<BatchDatesResponse> getBatchDates(@PathVariable Integer batchId) {
        BatchDatesResponse response = service.getCourseBatchDetails(batchId);
        return ResponseEntity.ok(response);
    }

    // 2. Get Course Fee by CourseTrackId
    @GetMapping("/{admissionNo}/campus-zone")
    public ResponseEntity<CampusAndZoneDTO> getCampusAndZoneByAdmissionNo(@PathVariable String admissionNo) {
        CampusAndZoneDTO campusAndZone = service.getCampusAndZoneByAdmissionNo(admissionNo);

        if (campusAndZone != null) {
            return ResponseEntity.ok(campusAndZone);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/course-fee/campus/{cmpsId}/track/{courseTrackId}/batch/{courseBatchId}")
    public ResponseEntity<Float> getCourseFee(
            @PathVariable int cmpsId,
            @PathVariable int courseTrackId,
            @PathVariable int courseBatchId) {

        Optional<Float> courseFee = service.getCourseFeeByDetails(cmpsId, courseTrackId, courseBatchId);

        return courseFee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/details/{admissionNo}")
    public ResponseEntity<StudentDetailsDTO> getStudentDetailsByAdmission(@PathVariable String admissionNo) {
        return service.getStudentDetailsByAdmissionNo(admissionNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @GetMapping("/streams")
    public ResponseEntity<List<Stream>> getStreams() {
        try {
            return ResponseEntity.ok(service.getStreams());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/programs")
    public ResponseEntity<List<ProgramName>> getPrograms() {
        try {
            return ResponseEntity.ok(service.getPrograms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/exam-programs")
    public ResponseEntity<List<ExamProgram>> getExamPrograms() {
        try {
            return ResponseEntity.ok(service.getExamPrograms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/course-tracks")
    public ResponseEntity<List<CourseTrack>> getCourseTracks() {
        try {
            return ResponseEntity.ok(service.getCourseTracks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/course-batches")
    public ResponseEntity<List<CourseBatch>> getCourseBatches() {
        try {
            return ResponseEntity.ok(service.getCourseBatches());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getSections() {
        try {
            return ResponseEntity.ok(service.getSections());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/concession-reasons")
    public ResponseEntity<List<ConcessionReason>> getConcessionReasons() {
        try {
            return ResponseEntity.ok(service.getConcessionReasons());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/employee-details")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeDetails(@RequestParam String admissionNo) {
        Optional<EmployeeDetailsDTO> employeeDetails = service.getEmployeeDetailsByAdmissionNo(admissionNo);
        return employeeDetails.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}