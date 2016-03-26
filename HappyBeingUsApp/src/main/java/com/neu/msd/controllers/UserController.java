/**
 * 
 */
package com.neu.msd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.UserService;

/**
 * @author Harsh
 *
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/redirectToDiagnostic.action", method=RequestMethod.GET)
	public String redirectToDiagnostic(Model model){
		
		try {
			List<AdminActivityAnswer> activityAnswers = userService.getDiagnosticQuestions();
			model.addAttribute("activityAnswers", activityAnswers);
			return "diagnostic";
		} catch (UserException e) {
			return "errorPage";
		} catch (AdminException e) {
			return "errorPage";
		}
	}

}
