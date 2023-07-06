package com.marketingapp1.controller;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketingapp1.Dto.LeadDto;
import com.marketingapp1.entitities.Lead;
import com.marketingapp1.service.LeadService;
import com.marketingapp1.service.LeadServiceImpl;

@Controller
public class LeadController {

	@Autowired
//we have to call leadservice here
	private LeadService leadService;

	// http://loalhost:8080/create
	@RequestMapping("/create")
	public String viewCreateLead() {
		return "create_lead";
	}

	@RequestMapping("/saveLead")
	//we can use model or modelmap
	public String saveLead(@ModelAttribute Lead lead, Model model) {
//		System.out.println(lead.getFirstName());
//		System.out.println(lead.getLastName());
//		System.out.println(lead.getEmail());
//		System.out.println(lead.getMobile());

		leadService.saveLead(lead);
		model.addAttribute("msg", "record is saved!");
        return "create_lead";
	}

//	//method 2 (not so intersting)
//@RequestMapping("/saveLead")
//public String saveLead(@RequestParam("firstName")String firstName,
//		@RequestParam("lastName")String lastName,
//		@RequestParam("email")String email,
//		@RequestParam("mobile")long mobile
//		) {]
//	Lead lead=new Lead();
//	lead.setFirstName(firstName);
//	lead.setLastName(lastName);
//	lead.setEmail(email);
//	lead.setMobile(mobile);
//	leadService.saveLead(lead);
//			return "create_lead";
//}

	// using any Dto class service and generate getter n setters
	// method 3 use only 1st and 3rd method
//	@RequestMapping("/saveLead")
//	public String saveLead(LeadDto leadDto) {
//
//		// create new entity object lead remove syso and put lead.set
//		Lead lead = new Lead();
//		lead.setFirstName(leadDto.getFirstName());
//		lead.setLastName(leadDto.getLastName());
//		lead.setEmail(leadDto.getEmail());
//		lead.setMobile(leadDto.getMobile());
//		leadService.saveLead(lead);
//		return "create_lead";
//
//	}
	// using the first method of savelead
	@RequestMapping("/Listall")
	public String getAllLead(Model model) {
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads",leads);
		return "search_result";
	}
	@RequestMapping("/delete")
	//in request param (id) is given because  delete by id is mentioned
	public String deleteLeadById(@RequestParam("id") long id,Model model) {
		leadService.deleteLeadById(id);
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads",leads);
		return "search_result";
}
	
	
	@RequestMapping("/update")
	public String getLeadById(@RequestParam("id") long id,Model model) {
		
		//add Lead lead=by yourself
		Lead lead=leadService.findLeadById(id);
		model.addAttribute("lead",lead);
	    return "update_lead";
		
}
	//remember we created action updateLead in jsp page
	@RequestMapping("/updateLead")
	public String updateLead(LeadDto dto,Model model) {
		//data transfer object
		Lead l=new Lead();
		l.setId(dto.getId());
		l.setFirstName(dto.getFirstName());
		l.setLastName(dto.getLastName());
		l.setEmail(dto.getEmail());
		l.setMobile(dto.getMobile());
		leadService.saveLead(l);
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads",leads);
		return "search_result";
		
	}
}