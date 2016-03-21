/**
 * 
 */
package com.neu.msd.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.service.AuthenticateService;

/**
 * @author NISHA
 *
 */
@Controller
public class AuthenticationController {
	
	@Autowired
	private AuthenticateService authenticateService;

	@RequestMapping(value="/landingPage.action", method=RequestMethod.GET)
	public String loadLandingPage(Model model){
		
		UserAuthentication userAuthentication = new UserAuthentication();
		DaughterRegistration daughterRegistration = new DaughterRegistration();
		MotherRegistration motherRegistration = new MotherRegistration();
		
		
		model.addAttribute("userAuthentication", userAuthentication);
		model.addAttribute("daughterRegistration", daughterRegistration);
		model.addAttribute("motherRegistration", motherRegistration);
		
		return "landingPage";
	}

	@RequestMapping(value="/signUp.action", method=RequestMethod.POST)
	public String registerDaughter(@ModelAttribute("daughterRegistration") DaughterRegistration daughterRegistration, Model model){
		
		try {
			authenticateService.registerDaughter(daughterRegistration);
			return "topics";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/getMotherByEmail.action", method=RequestMethod.POST)
	public String getMotherByEmail(@RequestParam("emailID") String emailID, Model model, HttpSession session){
		
		try {
			Mother mother = new Mother();
			mother = authenticateService.getMotherByEmail(emailID);
			
			if(mother.getId() == 0){
//			Delete when landing page has mother registration form.
				DaughterRegistration daughterRegistration = new DaughterRegistration();
				Daughter daughter = new Daughter();
				daughterRegistration.setDaughter(daughter);
				model.addAttribute("daughterRegistration", daughterRegistration);
				model.addAttribute("motherRegister", "false");
				return "landingPage";
			}
			
			MotherRegistration motherRegistration = new MotherRegistration();
			motherRegistration.setMother(mother);

//			Delete when landing page has mother registration form.
			DaughterRegistration daughterRegistration = new DaughterRegistration();
			Daughter daughter = new Daughter();
			daughterRegistration.setDaughter(daughter);
			model.addAttribute("daughterRegistration", daughterRegistration);


			model.addAttribute("motherRegistration", motherRegistration);
			model.addAttribute("motherRegister", "true");
			return "landingPage";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/topicPage.action", method=RequestMethod.POST)
	public String registerMother(@ModelAttribute("motherRegistration") MotherRegistration motherRegistration, Model model){
		
		try {
			int records = authenticateService.updateMotherDetails(motherRegistration);
			model.addAttribute("records", records);
			return "topics";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	
	@InitBinder
	public void anyNameHere(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
		
	}
}
