/**
 * 
 */
package com.neu.msd.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;

/**
 * @author NISHA
 *
 */
@Controller
public class AuthenticationController {

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
		
		return "landingPage";
	}
}
