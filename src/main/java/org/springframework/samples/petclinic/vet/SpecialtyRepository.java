
package org.springframework.samples.petclinic.vet;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import org.springframework.data.jpa.repository.Query;

public interface SpecialtyRepository extends Repository<Specialty, Integer> {

	void save(Specialty specialty);

	void delete(Specialty specialty);

	@Query("SELECT s FROM Specialty s")
	@Transactional(readOnly = true)
	List<Specialty> findAllSpecialty() throws DataAccessException;
}
