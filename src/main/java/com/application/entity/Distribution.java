package com.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sce_app_distribution", schema = "sce_application")
public class Distribution {

    @Id
    @Column(name = "app_distribution_id")
    private int appDistributionId;

    @Column(name = "app_start_no")
    private String appStartNo;

    @Column(name = "app_end_no")
    private String appEndNo;

    @Column(name = "total_app_count")
    private int totalAppCount;

    private float amount;

    @Column(name = "is_active")
    private int isActive;

    private int created_by;//logged employee id
    private int issued_to_emp_id;

    @ManyToOne
    @JoinColumn(name = "issued_by_type_id", referencedColumnName = "app_issued_id")
    private AppIssuedType issuedByType;

    @ManyToOne
    @JoinColumn(name = "issued_to_type_id", referencedColumnName = "app_issued_id")
    private AppIssuedType issuedToType;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;
}
