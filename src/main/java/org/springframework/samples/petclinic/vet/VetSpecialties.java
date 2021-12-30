package org.springframework.samples.petclinic.vet;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.samples.petclinic.model.BaseEntity;
import javax.persistence.Column;

import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "vet_specialties")
public class VetSpecialties extends BaseEntity{

	@Column(name = "specialty_id")
	@NotEmpty
	private Integer specialtyId;
}
