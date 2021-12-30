
package org.springframework.samples.petclinic.vet;
import java.util.*;

public class SpecialtyList {

	public List<Specialty> specialtyList;

	public List<Specialty> getSpecialtyList() {
		if (specialtyList == null) {
			specialtyList = new ArrayList<>();
		}
		return specialtyList;
	}

	public void setSpecialtyList(List<Specialty> specialtyList) {
		this.specialtyList = specialtyList;
	}

}
