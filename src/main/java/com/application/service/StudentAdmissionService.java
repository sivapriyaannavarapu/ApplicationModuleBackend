package com.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dto.*;
import com.application.dto.StudentAdmissionDTO.SiblingDTO;
import com.application.entity.*;
import com.application.repository.*;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentAdmissionService {

    private static final Logger logger = LoggerFactory.getLogger(StudentAdmissionService.class);

    // Existing Autowired Repositories (from your code)
    @Autowired private StudentAcademicDetailsRepository academicDetailsRepo;
    @Autowired private StudentPersonalDetailsRepository personalDetailsRepo;
    @Autowired private StudentAddressRepository addressRepo;
    @Autowired private SiblingRepository siblingRepo;
    @Autowired private AdmissionTypeRepository admissionTypeRepo;
    @Autowired private StudentTypeRepository studentTypeRepo;
    @Autowired private GenderRepository genderRepo;
    @Autowired private CampusRepository campusRepo;
    @Autowired private CourseGroupRepository courseGroupRepo;
    @Autowired private CourseBatchRepository courseBatchRepo;
    @Autowired private StateRepository stateRepo;
    @Autowired private DistrictRepository districtRepo;
    @Autowired private CampusSchoolTypeRepository schoolTypeRepo;
    @Autowired private QuotaRepository quotaRepo;
    @Autowired private StudentRelationRepository relationTypeRepo;
    @Autowired private StudentClassRepository classRepo;
    @Autowired private OrientationRepository orientationRepo;
    @Autowired private MandalRepository mandalRepo;
    @Autowired private CityRepository cityRepo;
    @Autowired private AcademicYearRepository academicYearRepository;
    @Autowired private SectionRepository sectionRepo;
    @Autowired private StatusRepository statusRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private PaymentDetailsRepository paymentDetailsRepo;
    @Autowired private PaymentModeRepository paymentModeRepo;
    @Autowired private StudentConcessionTypeRepository studentConcessionTypeRepo;
    @Autowired private ConcessionTypeRepository concessionTypeRepo;
    @Autowired private ConcessionReasonRepository concessionReasonRepo;
    @Autowired private StudentApplicationTransactionRepository appTransactionRepo;
    @Autowired private OrganizationRepository orgRepo;
    @Autowired private OrgBankRepository orgBankRepo;


    // Assumed Repositories for missing entities (ensure these are created)
    @Autowired private SchoolDetailsRepository schoolDetailsRepo; // For SchoolDetails
    @Autowired private OrgBankBranchRepository orgBankBranchRepo; // Already included, but ensuring clarity

    // Existing Dropdown Methods (unchanged, included for context)
    public List<GenericDropdownDTO> getAllAdmissionTypes() {
        return admissionTypeRepo.findAll().stream()
                .map(t -> new GenericDropdownDTO(t.getAdms_type_id(), t.getAdms_type_name()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllStudentTypes() {
        return studentTypeRepo.findAll().stream()
                .map(t -> new GenericDropdownDTO(t.getStud_type_id(), t.getStud_type())).collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllGenders() {
        return genderRepo.findAll().stream().map(g -> new GenericDropdownDTO(g.getGender_id(), g.getGenderName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllCampuses() {
        return campusRepo.findAll().stream().map(c -> new GenericDropdownDTO(c.getCampusId(), c.getCampusName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllCourses() {
        return courseGroupRepo.findAll().stream().map(c -> new GenericDropdownDTO(c.getGroup_id(), c.getGroup_name()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllCourseBatches() {
        return courseBatchRepo.findAll().stream()
                .map(b -> new GenericDropdownDTO(b.getCourseBatchId(), b.getCourseBatchName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllStates() {
        return stateRepo.findAll().stream().map(s -> new GenericDropdownDTO(s.getStateId(), s.getStateName()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getDistrictsByState(int stateId) {
        return districtRepo.findByStateStateId(stateId).stream()
                .map(d -> new GenericDropdownDTO(d.getDistrictId(), d.getDistrictName())).collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllSchoolTypes() {
        return schoolTypeRepo.findAll().stream()
                .map(s -> new GenericDropdownDTO(s.getSchool_type_id(), s.getSchool_type_name()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllQuotas() {
        return quotaRepo.findAll().stream().map(q -> new GenericDropdownDTO(q.getQuota_id(), q.getQuota_name()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllRelationTypes() {
        return relationTypeRepo.findAll().stream()
                .map(r -> new GenericDropdownDTO(r.getStudent_relation_id(), r.getStudent_relation_type()))
                .collect(Collectors.toList());
    }

    public List<GenericDropdownDTO> getAllClasses() {
        return classRepo.findAll().stream().map(c -> new GenericDropdownDTO(c.getClassId(), c.getClassName()))
                .collect(Collectors.toList());
    }

    // New Get By ID Methods for Requested Entities

    /**
     * Retrieve StudentType by ID
     */
    public GenericDropdownDTO getStudentTypeById(int id) {
        StudentType studentType = studentTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentType with ID " + id + " not found"));
        return new GenericDropdownDTO(studentType.getStud_type_id(), studentType.getStud_type());
    }

    /**
     * Retrieve Campus by ID
     */
    public GenericDropdownDTO getCampusById(int id) {
        Campus campus = campusRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campus with ID " + id + " not found"));
        return new GenericDropdownDTO(campus.getCampusId(), campus.getCampusName());
    }

    /**
     * Retrieve City by ID
     */
    public GenericDropdownDTO getCityById(int id) {
        City city = cityRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with ID " + id + " not found"));
        return new GenericDropdownDTO(city.getCityId(), city.getCityName());
    }

    /**
     * Retrieve CourseOrientation (Join Into) by ID
     */
    public GenericDropdownDTO getOrientationById(int id) {
        Orientation orientation = orientationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orientation with ID " + id + " not found"));
        return new GenericDropdownDTO(orientation.getOrientation_id(), orientation.getOrientation_type());
    }

    /**
     * Retrieve StudentClass (Select Class) by ID
     */
    public GenericDropdownDTO getStudentClassById(int id) {
        StudentClass studentClass = classRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentClass with ID " + id + " not found"));
        return new GenericDropdownDTO(studentClass.getClassId(), studentClass.getClassName());
    }

    /**
     * Retrieve CourseGroup (Course) by ID
     */
    public GenericDropdownDTO getCourseGroupById(int id) {
        CourseGroup courseGroup = courseGroupRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CourseGroup with ID " + id + " not found"));
        return new GenericDropdownDTO(courseGroup.getGroup_id(), courseGroup.getGroup_name());
    }

    /**
     * Retrieve CourseBatch by ID
     */
    public GenericDropdownDTO getCourseBatchById(int id) {
        CourseBatch courseBatch = courseBatchRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CourseBatch with ID " + id + " not found"));
        return new GenericDropdownDTO(courseBatch.getCourseBatchId(), courseBatch.getCourseBatchName());
    }

    /**
     * Retrieve State (School State) by ID
     */
    public GenericDropdownDTO getStateById(int id) {
        State state = stateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State with ID " + id + " not found"));
        return new GenericDropdownDTO(state.getStateId(), state.getStateName());
    }

    /**
     * Retrieve District (School District) by ID
     */
    public GenericDropdownDTO getDistrictById(int id) {
        District district = districtRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District with ID " + id + " not found"));
        return new GenericDropdownDTO(district.getDistrictId(), district.getDistrictName());
    }

    /**
     * Retrieve CampusSchoolType (School Type) by ID
     */
    public GenericDropdownDTO getSchoolTypeById(int id) {
        CampusSchoolType schoolType = schoolTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CampusSchoolType with ID " + id + " not found"));
        return new GenericDropdownDTO(schoolType.getSchool_type_id(), schoolType.getSchool_type_name());
    }

    /**
     * Retrieve SchoolDetails (School Name) by ID
     */
    public SchoolDetails getSchoolDetailsByName(String schoolName) {
        SchoolDetails schoolDetails = schoolDetailsRepo.findById(schoolName)
                .orElseThrow(() -> new EntityNotFoundException("SchoolDetails with name " + schoolName + " not found"));
        return schoolDetails;
    }

    /**
     * Retrieve Quota by ID
     */
    public GenericDropdownDTO getQuotaById(int id) {
        Quota quota = quotaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quota with ID " + id + " not found"));
        return new GenericDropdownDTO(quota.getQuota_id(), quota.getQuota_name());
    }

    /**
     * Retrieve StudentRelation (Relationship Type) by ID
     */
    public GenericDropdownDTO getRelationTypeById(int id) {
        StudentRelation relation = relationTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentRelation with ID " + id + " not found"));
        return new GenericDropdownDTO(relation.getStudent_relation_id(), relation.getStudent_relation_type());
    }

    /**
     * Retrieve Gender (Select Gender) by ID
     */
    public GenericDropdownDTO getGenderById(int id) {
        Gender gender = genderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender with ID " + id + " not found"));
        return new GenericDropdownDTO(gender.getGender_id(), gender.getGenderName());
    }

    /**
     * Retrieve ConcessionReason (Reason) by ID
     */
    public GenericDropdownDTO getConcessionReasonById(int id) {
        ConcessionReason reason = concessionReasonRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ConcessionReason with ID " + id + " not found"));
        return new GenericDropdownDTO(reason.getConc_reason_id(), reason.getConc_reason());
    }

    /**
     * Retrieve Mandal by ID
     */
    public GenericDropdownDTO getMandalById(int id) {
        Mandal mandal = mandalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mandal with ID " + id + " not found"));
        return new GenericDropdownDTO(mandal.getMandal_id(), mandal.getMandal_name());
    }

    /**
     * Retrieve Organization by ID
     */
    public GenericDropdownDTO getOrganizationById(int id) {
        Organization organization = orgRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization with ID " + id + " not found"));
        return new GenericDropdownDTO(organization.getOrg_id(), organization.getOrg_name());
    }

    /**
     * Retrieve OrgBank (Bank Name) by ID
     */
    public GenericDropdownDTO getOrgBankById(int id) {
        OrgBank bank = orgBankRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrgBank with ID " + id + " not found"));
        return new GenericDropdownDTO(bank.getOrg_bank_id(), bank.getBank_name());
    }

    /**
     * Retrieve OrgBankBranch (Branch Name with City) by ID
     */
//    public OrgBankBranchDTO getOrgBankBranchById(int id) {
//        OrgBankBranch branch = orgBankBranchRepo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("OrgBankBranch with ID " + id + " not found"));
//        String cityName = branch.getCity() != null ? branch.getCity().getCityName() : null;
//        return new OrgBankBranchDTO(branch.getOrg_bank_branch_id(), branch.getBranch_name(), cityName);
//    }

    // Existing Form Submission Logic (unchanged, included for completeness)
    @Transactional
    public void createNewAdmission(StudentAdmissionDTO formData) {
        // --- 1. Save Academic Details (The main record) ---
        StudentAcademicDetails academicDetails = new StudentAcademicDetails();
        academicDetails.setStudAdmsNo(formData.getStudAdmsNo());
        academicYearRepository.findById(26).ifPresent(academicDetails::setAcademicYear);
        academicDetails.setHt_no(formData.getHtNo());
        academicDetails.setFirst_name(formData.getStudentName());
        academicDetails.setLast_name(formData.getSurname());
        academicDetails.setPre_school_name(formData.getSchoolName());
        academicDetails.setAdmission_referred_by(formData.getAdmissionReferredBy());
        academicDetails.setScore_app_no(formData.getScoreAppNo());
        academicDetails.setScore_marks(formData.getMarks());
        academicDetails.setAdditional_course_fee(formData.getAdditionalCourseFee() != null ? formData.getAdditionalCourseFee() : 0);
        academicDetails.setAdms_date(LocalDate.now());
        academicDetails.setApp_sale_date(formData.getAppSaleDate());

        if (formData.getProId() == null) throw new IllegalArgumentException("Pro ID is mandatory and cannot be null.");
        if (formData.getClassId() == null) throw new IllegalArgumentException("Class ID is mandatory and cannot be null.");
        if (formData.getCampusId() == null) throw new IllegalArgumentException("Campus ID is mandatory and cannot be null.");
        if (formData.getCourseId() == null) throw new IllegalArgumentException("Course ID is mandatory and cannot be null.");

        StudentClass studentClass = classRepo.findById(formData.getClassId()).orElseThrow(() -> new IllegalArgumentException("Invalid Class ID: " + formData.getClassId() + " not found."));
        academicDetails.setStudentClass(studentClass);
        Campus campus = campusRepo.findById(formData.getCampusId()).orElseThrow(() -> new IllegalArgumentException("Invalid Campus ID: " + formData.getCampusId() + " not found."));
        academicDetails.setCampus(campus);
        academicDetails.setCourse_track_id(formData.getCourseId());

        if (formData.getCourseBatchId() != null) academicDetails.setCourse_batch_id(formData.getCourseBatchId());
        employeeRepo.findById(formData.getProId()).ifPresent(academicDetails::setEmployee);
        if (formData.getSectionId() != null) sectionRepo.findById(formData.getSectionId()).ifPresent(academicDetails::setSection);
        if (formData.getAppTypeId() != null) admissionTypeRepo.findById(formData.getAppTypeId()).ifPresent(academicDetails::setAdmissionType);
        if (formData.getStudentTypeId() != null) studentTypeRepo.findById(formData.getStudentTypeId()).ifPresent(academicDetails::setStudentType);
        if (formData.getGenderId() != null) genderRepo.findById(formData.getGenderId()).ifPresent(academicDetails::setGender);
        if (formData.getQuotaId() != null) quotaRepo.findById(formData.getQuotaId()).ifPresent(academicDetails::setQuota);
        if (formData.getJoinIntoId() != null) orientationRepo.findById(formData.getJoinIntoId()).ifPresent(academicDetails::setOrientation);
        if (formData.getStatusId() != null) statusRepo.findById(formData.getStatusId()).ifPresent(academicDetails::setStatus);
        if (formData.getSchoolTypeId() != null) schoolTypeRepo.findById(formData.getSchoolTypeId()).ifPresent(academicDetails::setCampusSchoolType);
        if (formData.getPreSchoolStateId() != null) stateRepo.findById(formData.getPreSchoolStateId()).ifPresent(academicDetails::setState);
        if (formData.getPreSchoolDistrictId() != null) districtRepo.findById(formData.getPreSchoolDistrictId()).ifPresent(academicDetails::setDistrict);

        academicDetails.setCreated_by(formData.getCreatedBY());
        academicDetails.setDoj(formData.getDateOfJoin());

        StudentAcademicDetails savedAcademicDetails = academicDetailsRepo.save(academicDetails);

        // --- 2. Save Personal Details ---
        StudentPersonalDetails personalDetails = new StudentPersonalDetails();
        personalDetails.setStudentAcademicDetails(savedAcademicDetails);
        personalDetails.setFather_name(formData.getFatherName());
        personalDetails.setOccupation(formData.getOccupation());
        personalDetails.setParent_mobile_no(formData.getPhoneNumber());
        personalDetails.setStud_aadhaar_no(formData.getAadharCardNo());
        personalDetails.setDob(formData.getDob());
        personalDetailsRepo.save(personalDetails);
        personalDetails.setCreated_by(formData.getCreatedBY());
        // --- 3. Save Address Details ---
        if (formData.getAddressDetails() != null) {
            AddressDetailsDTO addressDTO = formData.getAddressDetails();
            StudentAddress address = new StudentAddress();
            address.setStudentAcademicDetails(savedAcademicDetails);
            address.setHouse_no(addressDTO.getDoorNo());
            address.setStreet(addressDTO.getStreet());
            address.setLandmark(addressDTO.getLandmark());
            address.setArea(addressDTO.getArea());
            address.setCreated_by(addressDTO.getCreatedBy());
            if (addressDTO.getPincode() != null) address.setPostalCode(addressDTO.getPincode());
            if (addressDTO.getCityId() != null) cityRepo.findById(addressDTO.getCityId()).ifPresent(address::setCity);
            if (addressDTO.getMandalId() != null) mandalRepo.findById(addressDTO.getMandalId()).ifPresent(address::setMandal);
            if (addressDTO.getDistrictId() != null) districtRepo.findById(addressDTO.getDistrictId()).ifPresent(address::setDistrict);
            if (addressDTO.getStateId() != null) stateRepo.findById(addressDTO.getStateId()).ifPresent(address::setState);
            addressRepo.save(address);
        }

        // --- 4. Save Sibling Details ---
        if (formData.getSiblings() != null && !formData.getSiblings().isEmpty()) {
            for (SiblingDTO siblingDto : formData.getSiblings()) {
                Sibling sibling = new Sibling();
                sibling.setStudentAcademicDetails(savedAcademicDetails);
                sibling.setSibling_name(siblingDto.getFullName());
                sibling.setCreated_by(siblingDto.getCreatedBy());
                sibling.setSibling_school(siblingDto.getSchoolName());
                if (siblingDto.getClassId() != null) classRepo.findById(siblingDto.getClassId()).ifPresent(sibling::setStudentClass);
                if (siblingDto.getRelationTypeId() != null) relationTypeRepo.findById(siblingDto.getRelationTypeId()).ifPresent(sibling::setStudentRelation);
                if (siblingDto.getGenderId() != null) genderRepo.findById(siblingDto.getGenderId()).ifPresent(sibling::setGender);
                siblingRepo.save(sibling);
            }
        }

        // --- 5. Save Concession Details FIRST ---
        StudentConcessionType savedConcession = null;
        if (formData.getConcessionDetails() != null) {
            savedConcession = saveConcessionDetails(formData.getConcessionDetails(), savedAcademicDetails);
        }

        // --- 6. Save Payment Details AFTER Concession ---
        if (formData.getPaymentDetails() != null) {
            savePaymentDetails(formData.getPaymentDetails(), savedAcademicDetails, savedConcession);
        }
    }

    /**
     * Helper method to save payment details.
     */
    @Transactional
    private void savePaymentDetails(PaymentDetailstDTO paymentDetailsDTO, StudentAcademicDetails academicDetails, StudentConcessionType savedConcession) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStudentAcademicDetails(academicDetails);
        paymentDetails.setApp_fee(paymentDetailsDTO.getApplicationFeeAmount() != null ? paymentDetailsDTO.getApplicationFeeAmount() : 0.0f);
        paymentDetails.setPaid_amount(paymentDetailsDTO.getApplicationFeeAmount() != null ? paymentDetailsDTO.getApplicationFeeAmount() : 0.0f);
        paymentDetails.setCreated_by(academicDetails.getCreated_by());
        paymentDetails.setPre_print_receipt_no(paymentDetailsDTO.getPrePrintedReceiptNo());
        paymentDetails.setApplication_fee_pay_date(paymentDetailsDTO.getApplicationFeeDate());
        paymentDetails.setConc_amount(paymentDetailsDTO.getConcessionAmount());
        paymentDetails.setAcedemicYear(academicDetails.getAcademicYear());
        paymentDetails.setStudentClass(academicDetails.getStudentClass());
        paymentDetails.setStatus(academicDetails.getStatus());
        paymentDetails.setStudentConcessionType(savedConcession);

        if (paymentDetailsDTO.getPaymentModeId() != null) {
            paymentModeRepo.findById(paymentDetailsDTO.getPaymentModeId()).ifPresent(paymentDetails::setPaymenMode);
        }

        PaymentMode paymentMode = paymentDetails.getPaymenMode();
        if (paymentMode != null) {
            String paymentType = paymentMode.getPayment_type();
            if ("DD".equals(paymentType) || "Cheque".equals(paymentType)) {
                StudentApplicationTransaction transaction = new StudentApplicationTransaction();

                transaction.setCheque_no(paymentDetailsDTO.getChequeDdNo());
                transaction.setDd_no(paymentDetailsDTO.getChequeDdNo());
                transaction.setIfsc_code(paymentDetailsDTO.getIfscCode());
                transaction.setApplication_fee_pay_date(paymentDetailsDTO.getApplicationFeeDate());
                transaction.setDate(new java.util.Date());

                if (paymentDetailsDTO.getCityId() != null && paymentDetailsDTO.getCityId() > 0) {
                    cityRepo.findById(paymentDetailsDTO.getCityId()).ifPresent(transaction::setCity);
                }

                if (paymentDetailsDTO.getOrgBankId() != null && paymentDetailsDTO.getOrgBankId() > 0) {
                    OrgBank bank = orgBankRepo.findById(paymentDetailsDTO.getOrgBankId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Bank ID provided: " + paymentDetailsDTO.getOrgBankId()));
                    transaction.setOrgBank(bank);
                } else {
                    throw new IllegalArgumentException("Bank information is required for Cheque/DD payments.");
                }

                if (paymentDetailsDTO.getOrgBankBranchId() != null && paymentDetailsDTO.getOrgBankBranchId() > 0) {
                    OrgBankBranch branch = orgBankBranchRepo.findById(paymentDetailsDTO.getOrgBankBranchId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Bank Branch ID provided: " + paymentDetailsDTO.getOrgBankBranchId()));
                    transaction.setOrgBankBranch(branch);
                }

                if (paymentDetailsDTO.getCreatedBy() != null) {
                    transaction.setCreated_by(paymentDetailsDTO.getCreatedBy());
                }

                if (paymentDetailsDTO.getOrganizationId() != null) {
                    transaction.setOrg_id(paymentDetailsDTO.getOrganizationId());
                }

                appTransactionRepo.save(transaction);
            }
        }

        paymentDetailsRepo.save(paymentDetails);
    }

    /**
     * Helper method to save concession details.
     */
    @Transactional
    private StudentConcessionType saveConcessionDetails(StudentConcessionDetailsDTO concessionDetailsDTO, StudentAcademicDetails academicDetails) {
        StudentConcessionType concession = new StudentConcessionType();

        concession.setStudAdmsId(academicDetails.getStud_adms_id());
        concession.setAcademicYear(academicDetails.getAcademicYear());
        concession.setConc_amount(concessionDetailsDTO.getConcessionAmount());
        concession.setConc_issued_by(concessionDetailsDTO.getConcessionIssuedBy());
        concession.setConc_authorised_by(concessionDetailsDTO.getConcessionAuthorisedBy());
        concession.setCreated_by(concessionDetailsDTO.getCreatedBy());
        concession.setCreated_Date(LocalDateTime.now());

        // CRITICAL FIX: Use orElseThrow to ensure the ConcessionType exists.
        if (concessionDetailsDTO.getConcTypeId() != null) {
            ConcessionType concessionType = concessionTypeRepo.findById(concessionDetailsDTO.getConcTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("ConcessionType not found with ID: " + concessionDetailsDTO.getConcTypeId()));
            concession.setConcessionType(concessionType);
        } else {
            // Explicitly handle the null case for concTypeId as per the database constraint.
            throw new IllegalArgumentException("Concession Type ID cannot be null.");
        }

        if (concessionDetailsDTO.getConcessionReasonId() != null) {
            concessionReasonRepo.findById(concessionDetailsDTO.getConcessionReasonId())
                    .ifPresent(concession::setConcessionReason);
        }

        return studentConcessionTypeRepo.save(concession);
    }
}