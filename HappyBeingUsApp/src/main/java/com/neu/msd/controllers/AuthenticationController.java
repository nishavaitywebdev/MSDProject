/**
 * 
 */
package com.neu.msd.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.entities.User;
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
	public String registerDaughter(@ModelAttribute("daughterRegistration") DaughterRegistration daughterRegistration, Model model,HttpSession session){
		
		try {
			authenticateService.registerDaughter(daughterRegistration);
			UserAuthentication userAuthentication=new UserAuthentication();
			userAuthentication.setUsername(daughterRegistration.getUsername());
			userAuthentication.setPassword(userAuthentication.getPassword());
			User user=authenticateService.validUser(userAuthentication);
			session.setAttribute("user", user);
			return "redirect:/redirectToDiagnostic.action";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/getMotherByEmail.action", method=RequestMethod.POST)
	public String getMotherByEmail(@RequestParam("emailID") String emailID, Model model, HttpSession session){
		
		try {
			MotherRegistration motherRegistration = authenticateService.getMotherRegistrationByEmail(emailID);
			
			DaughterRegistration daughterRegistration = new DaughterRegistration();
			model.addAttribute("daughterRegistration", daughterRegistration);
			model.addAttribute("motherRegistration", motherRegistration);

			String invalidMotherEmailErr ="";

			if(motherRegistration.getMother().getId() == 0){
				invalidMotherEmailErr ="Please check the emailId you have entered.";
				model.addAttribute("motherRegister", "false");
				model.addAttribute("motherRegisterErr", invalidMotherEmailErr);
			}else if(null != motherRegistration.getUsername()){
				invalidMotherEmailErr ="The account already exists, please log in...";
			model.addAttribute("motherRegister", "false");
				model.addAttribute("motherRegisterErr", invalidMotherEmailErr);
			}
			else{
				
				model.addAttribute("motherRegister", "true");
			}
		
			return "landingPage";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/Login.action", method=RequestMethod.POST)
	public String loginUser(@ModelAttribute("userAuthentication") UserAuthentication userAuthentication, Model model, HttpSession session){
		try {
			User user=authenticateService.validUser(userAuthentication);
			session.setAttribute("user", user);
			if (user==null)
			{
				DaughterRegistration daughterRegistration = new DaughterRegistration();
				MotherRegistration motherRegistration = new MotherRegistration();
				model.addAttribute("daughterRegistration", daughterRegistration); 
				model.addAttribute("motherRegistration", motherRegistration); 
				model.addAttribute("usernameerr", "false");
				return "landingPage";
			}
			else
				if(user.getVersion()==null)
				{
					return "redirect:/redirectToDiagnostic.action";
				}
			return "userhome";
			
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
	@RequestMapping(value="/forgotPassword.action", method=RequestMethod.POST)
	public String resetUnamePassword(@RequestParam("emailID") String emailID, @RequestParam("username") String username, 
			@RequestParam("password") String password, Model model){
		
		try {
			DaughterRegistration daughterRegistration = new DaughterRegistration();
			MotherRegistration motherRegistration = new MotherRegistration();
			model.addAttribute("daughterRegistration", daughterRegistration); 
			model.addAttribute("motherRegistration", motherRegistration); 

			String status = authenticateService.resetUnamePassword(emailID,username,password);
			return "landingPage";
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkUsernameAvailability.action", method = RequestMethod.POST)
	public String checkUsernameAvailability(@RequestParam("userName") String uname)
	{
		try {
			return authenticateService.checkUname(uname);
		} catch (AuthenticationException e) {
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkEmailExists.action", method = RequestMethod.POST)
	public String checkEmailExists(@RequestParam("email") String email)
	{
		try {
			return authenticateService.checkEmail(email);
		} catch (AuthenticationException e) {
			return "error";
		}
	}
	
	@InitBinder
	public void anyNameHere(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
		
	}
}
