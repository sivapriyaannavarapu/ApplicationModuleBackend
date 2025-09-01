package com.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mandal {
	
	@Id
	private int mandal_id;
	private String mandal_name;
	
	@ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
	
	@ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
