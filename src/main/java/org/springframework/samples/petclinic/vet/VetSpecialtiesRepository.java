
package org.springframework.samples.petclinic.vet;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VetSpecialtiesRepository extends Repository<VetSpecialties, Integer> {

@Transactional
@Modifying
@Query("DELETE FROM VetSpecialties WHERE vet_id=:vet_id")
void deleteByVetId(@Param("vet_id") Integer vetId) throws DataAccessException;

@Modifying
@Query(value = "insert into vet_specialties (vet_id, specialty_id) VALUES (:vet_id, :specialty_id)", nativeQuery = true)
@Transactional
void addByVetId_(@Param("vet_id") Integer vetId, @Param("specialty_id") Integer specialtyId);
/*
@Transactional
@Modifying
@Query("UPDATE VetSpecialties u SET vet_id=:vet_id, specialty_id=:specialty_id")
void addByVetId(@Param("vet_id") Integer vetId, @Param("specialty_id") Integer specialtyId) throws DataAccessException;
*/
}
 