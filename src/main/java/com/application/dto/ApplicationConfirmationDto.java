package com.application.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ApplicationConfirmationDto {

    private String admissionNo;
    private String firstName;
    private String lastName;
    private String parentName;
    private Integer gender;
    private Double applicationFee;

    // Concessions (3 entries expected)
    private List<ConcessionDTO> concessions;

    // Second form fields
    private Integer joinYearId;
    private Integer streamId;
    private Integer programId;
    private Integer examProgramId;
    private Integer courseTrackId;
    private Integer batchId;
    private Integer sectionId;
    private Date app_conf_date;

    // Not posted, just auto-populated in UI
//    private transient LocalDate courseBatchStartDate;
//    private transient LocalDate courseBatchEndDate;
//    private transient Double courseFee;
}

