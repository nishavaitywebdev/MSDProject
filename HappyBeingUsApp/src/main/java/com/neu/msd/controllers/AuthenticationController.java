/**
 * 
 */
package com.neu.msd.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;
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
		
		model.addAttribute("userAuthentication", userAuthentication);
		model.addAttribute("daughterRegistration", daughterRegistration);
		
		return "landingPage";
	}

	@RequestMapping(value="/signUp.action", method=RequestMethod.POST)
	public String registerDaughter(@ModelAttribute("daughterRegistration") DaughterRegistration daughterRegistration, Model model){
		
		try {
			authenticateService.registerDaughter(daughterRegistration);
			return "landingPage";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	@RequestMapping(value="/Login.action", method=RequestMethod.POST)
	public String loginUser(@ModelAttribute("userAuthentication") UserAuthentication userAuthentication, Model model){
		try {
			int id=authenticateService.validUser(userAuthentication);
			model.addAttribute("id", id);
			if (id==-1)
			{
				return null;
			}
			else
			return "userHome";
			
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
