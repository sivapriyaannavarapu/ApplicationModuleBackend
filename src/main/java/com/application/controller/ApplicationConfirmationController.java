package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.ApplicationConfirmationDto;
import com.application.entity.AcademicYear;
import com.application.entity.ConcessionReason;
import com.application.entity.ConcessionType;
import com.application.entity.CourseBatch;
import com.application.entity.CourseTrack;
import com.application.entity.ExamProgram;
import com.application.entity.ProgramName;
import com.application.entity.Section;
import com.application.entity.Stream;
import com.application.service.ApplicationConfirmationService;

@RestController
@RequestMapping("/api/admissions")
public class ApplicationConfirmationController {

    @Autowired
    private ApplicationConfirmationService admissionService;

    // Save/Update
    @PostMapping("/save")
    public ApplicationConfirmationDto saveOrUpdate(@RequestBody ApplicationConfirmationDto dto) {
        return admissionService.saveOrUpdate(dto);
    }

    // Get by Admission No
    @GetMapping("/{admissionNo}")
    public ApplicationConfirmationDto getByAdmissionNo(@PathVariable String admissionNo) {
        return admissionService.getByAdmissionNo(admissionNo);
    }

    // -------- Dropdowns ----------
    @GetMapping("/academic-years")
    public List<AcademicYear> getAcademicYears() { return admissionService.getAcademicYears(); }

    @GetMapping("/streams")
    public List<Stream> getStreams() { return admissionService.getStreams(); }

    @GetMapping("/programs")
    public List<ProgramName> getPrograms() { return admissionService.getPrograms(); }

    @GetMapping("/exam-programs")
    public List<ExamProgram> getExamPrograms() { return admissionService.getExamPrograms(); }

    @GetMapping("/course-tracks")
    public List<CourseTrack> getCourseTracks() { return admissionService.getCourseTracks(); }

    @GetMapping("/course-batches")
    public List<CourseBatch> getCourseBatches() { return admissionService.getCourseBatches(); }

    @GetMapping("/sections")
    public List<Section> getSections() { return admissionService.getSections(); }

    @GetMapping("/concession-types")
    public List<ConcessionType> getConcessionTypes() { return admissionService.getConcessionTypes(); }

    @GetMapping("/concession-reasons")
    public List<ConcessionReason> getConcessionReasons() { return admissionService.getConcessionReasons(); }
}
