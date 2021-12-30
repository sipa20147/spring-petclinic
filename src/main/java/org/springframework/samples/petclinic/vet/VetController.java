/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*NEW*/
import javax.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
/*NEW*/

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class VetController {

	private static final String VIEWS_VET_UPDATE_FORM = "vets/updateVetForm";

	private final VetRepository vets;

	private final SpecialtyRepository special;

	private final VetSpecialtiesRepository vetSpecialties;

/* Почему называется clinicService, а не просто типа vet? */ 
	public VetController(VetRepository clinicService, SpecialtyRepository special, VetSpecialtiesRepository vetSpecialties) {
		this.vets = clinicService;
		this.special = special;
		this.vetSpecialties = vetSpecialties;
	}

	@ModelAttribute("specialties")
	public List<Specialty> allSpecialty() {
		return this.special.findAllSpecialty();
	}

/*NEW*/
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
/*NEW*/

	@GetMapping("/vets.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		Page<Vet> paginated = findPaginated(page);
		vets.getVetList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	private String addPaginationModel(int page, Page<Vet> paginated, Model model) {
		model.addAttribute("listVets", paginated);
		List<Vet> listVets = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listVets", listVets);
		return "vets/vetList";
	}

	private Page<Vet> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return vets.findAll(pageable);
	}

	@GetMapping({ "/vets" })
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vets.findAll());
		return vets;
	}

	@GetMapping("/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, Model model) {
		Vet vet = this.vets.findById(vetId);

		List<Specialty> vetSpecialtyListAll = this.special.findAllSpecialty();
		List<Specialty> vetSpecialtyListCurrent = vet.getSpecialties();

		List<Specialty> temp = new ArrayList<Specialty>();
		for (Specialty spAll : vetSpecialtyListAll) {
			temp.add(spAll);
		}

		for (Specialty spAll : temp) {
			for (Specialty spCurr : vetSpecialtyListCurrent) {
				if(spCurr.getName() == spAll.getName()) {
					spAll.setAssigned("on");	
			}
			System.out.println(spAll.getAssigned() + ", " + spAll.getName());
			}
		}

		vet.vetSpecialtyList = temp;
		model.addAttribute(vet);
		return VIEWS_VET_UPDATE_FORM;
	}

	@PostMapping("/vets/{vetId}/edit")
	public String processUpdateVetForm(@Valid Vet vet,  BindingResult result, @PathVariable("vetId") int vetId, Model model) {

		System.out.println("hello - start");
		System.out.println("===============");

		if (result.hasErrors()) {
			System.out.println("error");
			return VIEWS_VET_UPDATE_FORM;
		}
		else {
			vet.setId(vetId);
			this.vets.save(vet);

			this.vetSpecialties.deleteByVetId(vetId);
			List<Specialty> vetSpecialtyNew = vet.getVetSpecialtyList();

			for (Specialty specialNew : vetSpecialtyNew) {
				System.out.println(specialNew.getAssigned() + ", " + specialNew.getName());
				}	

			for (Specialty specialNew : vetSpecialtyNew) {
				if(specialNew.assigned == "on"){
				int specialtyId = specialNew.getId();
				this.vetSpecialties.addByVetId_(vetId, specialtyId);
				}
				}
			
			System.out.println("===============");
			System.out.println("hello - end");
			return "redirect:/vets/{vetId}";
		}
	}

	@GetMapping("/vets/{vetId}")
	public ModelAndView showVet(@PathVariable("vetId") int vetId) {
		ModelAndView mav = new ModelAndView("vets/vetDetails");
		Vet vet = this.vets.findById(vetId);
		mav.addObject(vet);
		return mav;
	}

	@PostMapping("/vets/{vetId}/remove")
	public String DeleteVet(@PathVariable("vetId") int vetId, Model model) {
		Vet vet = this.vets.findById(vetId);
		this.vetSpecialties.deleteByVetId(vetId);
		this.vets.deleteByVetId(vetId);
		return "redirect:/vets.html";
	}

	@GetMapping(value = "/test")
	public String showCheckbox(Model model) {
	boolean myBooleanVariable = false;
	model.addAttribute("myBooleanVariable", myBooleanVariable);
	return "sample-checkbox";
}

}
